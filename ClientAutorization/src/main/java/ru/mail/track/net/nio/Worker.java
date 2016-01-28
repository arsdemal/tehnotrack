package ru.mail.track.net.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.net.ConnectionHandler;
import ru.mail.track.net.MessageListener;
import ru.mail.track.net.Protocol;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.net.ProtocolException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
public class Worker implements ConnectionHandler {

    static Logger log = LoggerFactory.getLogger(Worker.class);

    // подписчики
    private List<MessageListener> listeners = new ArrayList<>();
    // очередь на исполнение событий
    private BlockingQueue eventQueue;

    private Protocol protocol;
    private ChannelManager channelManager;
    private SocketChannel socket;
    private NioServer server;

    public Worker(Protocol protocol, ChannelManager channelManager, BlockingQueue eventQueue){
        this.protocol = protocol;
        this.channelManager = channelManager;
        this.eventQueue = eventQueue;
    }

    public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);
        synchronized(eventQueue) {
            eventQueue.add(new ServerDataEvent(server, socket, dataCopy));
            eventQueue.notify();
        }
    }

    public void notifyListeners(Session session, Message msg) {
        listeners.forEach(it -> it.onMessage(session, msg));
    }



    @Override
    public void send(Session session, Message msg) throws IOException {
        server.send(channelManager.getSocket(session), protocol.encode(msg));
    }


    @Override
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {

        ServerDataEvent dataEvent = null;

        while (!Thread.currentThread().isInterrupted()) {
            // Wait for data to become available
            synchronized (eventQueue) {
                while (eventQueue.isEmpty()) {
                    try {
                        eventQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    dataEvent = (ServerDataEvent) eventQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Session session = channelManager.getSession(dataEvent.socket);
                Message msg = null;

                try {
                    msg = protocol.decode(Arrays.copyOf(dataEvent.data, dataEvent.data.length));
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                msg.setSender(session.getId());
                log.debug("message received: {}", msg);
                session.setConnectionHandler(this);
                server = dataEvent.server;
                socket = dataEvent.socket;
                notifyListeners(session, msg);
            }
        }
    }
}
