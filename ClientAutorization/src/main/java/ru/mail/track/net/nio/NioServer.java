package ru.mail.track.net.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.CommandHandler;
import ru.mail.track.message.Message;
import ru.mail.track.net.ConnectionHandler;
import ru.mail.track.net.Protocol;
import ru.mail.track.net.Server;
import ru.mail.track.net.SessionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 */
public class NioServer implements Runnable,Server {

    static Logger log = LoggerFactory.getLogger(NewNioServer.class);

    public static final int PORT = 19000;

    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(16); // буфер, с которым будем работать
    private Map<SocketChannel, ByteBuffer> dataToWrite = new ConcurrentHashMap<>(); // Данные для записи в канал

    private ExecutorService service = Executors.newFixedThreadPool(5);


    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;

    public NioServer(Protocol protocol, SessionManager sessionManager, CommandHandler commandHandler) throws Exception {
        selector = Selector.open();

        // Установили протокол передачи сообщений
        this.protocol = protocol;

        // Управление сессиями
        this.sessionManager = sessionManager;

        // Выполнение команд
        this.commandHandler = commandHandler;



        // Это серверный сокет
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        // Привязали его к порту
        socketChannel.socket().bind(new InetSocketAddress(PORT));

        // Должен быть неблокирующий для работы через selector
        socketChannel.configureBlocking(false);

        // Нас интересует событие коннекта клиента (как и для Socket - ACCEPT)
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {



        while (true) {
            try {
                log.info("Waiting on select()");

                // Блокируемся до получения евента на зарегистрированных каналах
                int num = selector.select();
                log.info("Raised events on {} channels", num);

                // Смторим, кто сгенерил евенты
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                // Проходим по всем источникам
                while (it.hasNext()) {
                    SelectionKey key = it.next();

                    // Если кто-то готов присоединиться
                    if (key.isAcceptable()) {
                        log.info("[acceptable]");

                        // Создаем канал для клиента и регистрируем его в селекторе
                        // Для нас интересно событие, когда клиент будет писать в канал
                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                    } else if (key.isReadable()) {
                        log.info("[readable]");

                        // По ключу получаем соответствующий канал
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuffer.clear(); // чистим перед использование

                        int numRead;
                        try {
                            // читаем данные в буфер
                            numRead = socketChannel.read(readBuffer);

                        } catch (IOException e) {
                            // Ошибка чтения - закроем это соединений и отменим ключ в селекторе
                            log.error("Failed to read data from channel", e);
                            key.cancel();
                            socketChannel.close();
                            break;
                        }

                        if (numRead == -1) {
                            // С нами оборвали соединение со стороны клиента
                            log.error("Failed to read data from channel (-1)");
                            key.channel().close();
                            key.cancel();
                            break;
                        }

                        if (numRead > 0) {
                            Message msg = protocol.decode(Arrays.copyOf(readBuffer.array(), numRead));
                            //msg.setSender(session.getId());
                            log.debug("message received: {}", msg);
                        }

                        log.info("read: {}", new String(readBuffer.array()));

                        // Чтобы читать данные ИЗ буфера, делаем flip()
                        readBuffer.flip();

                        service.submit(() -> {
                            dataToWrite.put(socketChannel,readBuffer );
                            key.interestOps(SelectionKey.OP_WRITE);
                            selector.wakeup();
                        });

                        // В качестве эхо-сервера, кладем то, что получили от клиента обратно в канал на запись
                        //dataToWrite.put(socketChannel, );ByteBuffer.wrap("Hello".getBytes())

                        // Меняем состояние канала - теперь он готов для записи и в следующий select() он будет isWritable();


                    } else if (key.isWritable()) {
                        log.info("[writable]");

                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer data = dataToWrite.get(socketChannel);
                        log.info("write: {}", new String(data.array()));

                        socketChannel.write(data);

                        // Меняем состояние канала - теперь он готов для чтения и в следующий select() он будет isReadable();
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }

                // Нужно почитстить обработанные евенты
                keys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void destroyServer() {

    }
}