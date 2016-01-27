package ru.mail.track.net.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.net.Protocol;
import ru.mail.track.net.SerializeProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 *
 */
public class NioClient {

    static Logger log = LoggerFactory.getLogger(NioClient.class);


    public static final int PORT = 9090;

    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer = ByteBuffer.allocate(128);
    private NioInputHandler inputHandler;
    private Protocol protocol;

    BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

    public void init() throws Exception {


        // Слушаем ввод данных с консоли
        Thread t = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    log.info("Exit!");
                    System.exit(0);
                }

                try {
                    queue.put(line);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

                // Будим селектор
                SelectionKey key = channel.keyFor(selector);
                log.info("wake up: {}", key.hashCode());
                key.interestOps(SelectionKey.OP_WRITE);
                selector.wakeup();
            }
        });
        t.start();

        this.inputHandler = new NioInputHandler();
        this.protocol = new SerializeProtocol();

        selector = Selector.open();
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);

        channel.connect(new InetSocketAddress("localhost", PORT));

        while (true) {

            log.info("Waiting on select()...");

            int num = selector.select();
            log.info("Raised {} events", num);


            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey sKey = keyIterator.next();

                if (sKey.isConnectable()) {
                    log.info("[connectable] {}", sKey.hashCode());

                    try {
                        channel.finishConnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // теперь в канал можно писать
                    sKey.interestOps(SelectionKey.OP_WRITE);

                } else if (sKey.isReadable()) {
                    log.info("[readable]");

                    buffer.clear();
                    int numRead = channel.read(buffer);
                    if (numRead < 0) {
                        break;
                    }

                    Message msg = protocol.decode(buffer.array());


                    //Message msg = protocol.decode(dataCopy);
                    log.info("From server: {}", msg.toString());

                } else if (sKey.isWritable()) {
                    log.info("[writable]");

                    String line = queue.poll();

                    if (line != null) {
                        Message message = inputHandler.processInput(line);
                        channel.write(ByteBuffer.wrap(protocol.encode(message)));

                    }
                    // Ждем записи в канал
                    sKey.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        NioClient client = new NioClient();
        client.init();
    }
}