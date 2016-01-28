package ru.mail.track.net.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.CommandHandler;
import ru.mail.track.net.Protocol;
import ru.mail.track.net.Server;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 */
public class NioServer implements Runnable,Server {

    static Logger log = LoggerFactory.getLogger(NioServer.class);

    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;
    private InetAddress hostAddress;
    private int port;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
    private Worker worker;
    private ChannelManager channelManager;
    private ExecutorService service;

    private List changeRequests = new LinkedList();
    private Map pendingData = new HashMap();
    private BlockingQueue<ServerDataEvent> eventQueue = new ArrayBlockingQueue<>(10);
    private Future future;




    public NioServer(Protocol protocol, SessionManager sessionManager, CommandHandler commandHandler) throws IOException {

        this.protocol = protocol;
        this.sessionManager = sessionManager;
        this.channelManager = new ChannelManager();
        this.commandHandler = commandHandler;
        this.hostAddress = null;
        this.port = 9090;
        selector = initSelector();

        // Запускаем обработчиков
        service  = Executors.newFixedThreadPool(5);
        worker = new Worker(protocol, channelManager, eventQueue);
        worker.addListener(commandHandler);
        this.future = service.submit(worker);

    }

    private Selector initSelector() throws IOException {

        Selector socketSelector = SelectorProvider.provider().openSelector();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        InetSocketAddress isa = new InetSocketAddress(this.hostAddress, this.port);
        serverChannel.socket().bind(isa);
        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        return socketSelector;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {

                synchronized(changeRequests) {
                    Iterator changes = this.changeRequests.iterator();
                    while (changes.hasNext()) {
                        ChangeRequest change = (ChangeRequest) changes.next();
                        switch(change.type) {
                            case ChangeRequest.CHANGEOPS:
                                SelectionKey key = change.socket.keyFor(this.selector);
                                key.interestOps(change.ops);
                        }
                    }
                    this.changeRequests.clear();
                }

                log.info("Waiting on select()");
                int num = selector.select();
                log.info("Raised events on {} channels", num);
                Iterator selectedKeys = this.selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        log.info("[acceptable]");
                        accept(key);
                    } else if (key.isReadable()) {
                        log.info("[readable]");
                        read(key);
                    } else if (key.isWritable()) {
                        log.info("[writable]");
                        write(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

    public void send(SocketChannel socket, byte[] data) {
        synchronized (changeRequests) {
            changeRequests.add(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));

            synchronized (pendingData) {
                List queue = (List) pendingData.get(socket);
                if (queue == null) {
                    queue = new ArrayList();
                    pendingData.put(socket, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }
        this.selector.wakeup();
    }

    private void accept(SelectionKey key) throws IOException {

        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        // Добавляем новую сессию и храним ее в sessionManager
        Session session = sessionManager.createSession();
        // Добавляем chanel-session в channelManager ( для поиска сессии )
        channelManager.addChannel(socketChannel, session);
    }

    private void read(SelectionKey key) throws IOException {

        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();

        int numRead;
        try {
            numRead = socketChannel.read(readBuffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }

        if (numRead == -1) {
            key.channel().close();
            key.cancel();
            return;
        }

        log.debug("count bytes {}", numRead);

        worker.processData(this,socketChannel,readBuffer.array(),numRead);

    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        synchronized (pendingData) {
            List queue = (List) pendingData.get(socketChannel);

            while (!queue.isEmpty()) {
                ByteBuffer buf = (ByteBuffer) queue.get(0);
                socketChannel.write(buf);
                if (buf.remaining() > 0) {
                    break;
                }
                queue.remove(0);
            }
            if (queue.isEmpty()) {
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    @Override
    public void destroyServer() {
        log.info("Stopping server...");
        while (!future.isDone());
        future.cancel(true);
        Thread.currentThread().interrupt();
    }

    @Override
    public void startServer() {
        log.info("Starting server...");
        Thread t = new Thread(this);
        t.start();
    }
}