package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.CommandHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class SocketServer implements Runnable,Server {

    public static final int PORT = 19000;

    static Logger log = LoggerFactory.getLogger(SocketServer.class);

    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;
    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;


    public SocketServer(Protocol protocol, SessionManager sessionManager, CommandHandler commandHandler) {
        try {
            this.protocol = protocol;
            this.sessionManager = sessionManager;
            this.commandHandler = commandHandler;
            sSocket = new ServerSocket(PORT);
            sSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void destroyServer() {
        isRunning = false;
        for (ConnectionHandler handler : handlers.values()) {
            handler.stop();
        }
    }

    @Override
    public void startServer() {

    }

    @Override
    public void run() {
        log.info("Started, waiting for connection");

        isRunning = true;
        while (isRunning) {
            try {
                Socket socket = sSocket.accept();
                log.info("Accepted. " + socket.getInetAddress());
                //создаем хендлер для связи с сервером
                ConnectionHandler handler = new SocketConnectionHandler(protocol, sessionManager.createSession(), socket);
                //добавляем хендлер команд, перехватывающий сообщения от хендлера связи
                handler.addListener(commandHandler);

                handlers.put(internalCounter.incrementAndGet(), handler);
                Thread thread = new Thread(handler);
                thread.start();
            } catch ( IOException e) {
                e.printStackTrace();
            }
        }
    }
}
