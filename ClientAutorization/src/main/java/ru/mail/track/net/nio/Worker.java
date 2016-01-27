package ru.mail.track.net.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.net.ConnectionHandler;
import ru.mail.track.net.MessageListener;
import ru.mail.track.net.Protocol;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Worker implements ConnectionHandler {

    static Logger log = LoggerFactory.getLogger(Worker.class);

    // подписчики
    private List<MessageListener> listeners = new ArrayList<>();
    private Protocol protocol;
    private ChannelManager channelManager;
    // очередь на исполнение событий
    private List queue = new LinkedList();

    private SocketChannel socket;
    private NioServer server;

    public Worker ( Protocol protocol, ChannelManager channelManager){
        this.protocol = protocol;
        this.channelManager = channelManager;
    }

    public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);
        synchronized(queue) {
            queue.add(new ServerDataEvent(server, socket, dataCopy));
            queue.notify();
        }
    }

    public void notifyListeners(Session session, Message msg) {
        listeners.forEach(it -> it.onMessage(session, msg));
    }



    @Override
    public void send(Message msg) throws IOException {
        server.send(socket, protocol.encode(msg));
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

        ServerDataEvent dataEvent;

        while(true) {
            // Wait for data to become available
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                dataEvent = (ServerDataEvent) queue.remove(0);
                Session session = channelManager.getSession(dataEvent.socket);
                Message msg = protocol.decode(Arrays.copyOf(dataEvent.data,dataEvent.data.length));
                msg.setSender(session.getId());
                log.debug("message received: {}", msg);

                session.setConnectionHandler(this);
                server = dataEvent.server;
                socket = dataEvent.socket;

                notifyListeners(session, msg);

                //Message infoMessage = commandHandler.onMessage(session,);

            }

            //Return to sender
        }

    }
}
