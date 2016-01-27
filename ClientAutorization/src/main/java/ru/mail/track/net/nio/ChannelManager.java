package ru.mail.track.net.nio;

import ru.mail.track.session.Session;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class ChannelManager {
    private Map<SocketChannel, Session> channelMap;
    private AtomicLong channelCounter = new AtomicLong(0);

    public ChannelManager() {
        channelMap = new HashMap<>();
    }

    public void addChannel(SocketChannel socketChannel, Session session) {
        channelMap.put(socketChannel,session);
    }

    public Session getSession(SocketChannel socketChannel) {
        Session session = channelMap.get(socketChannel);
        return session;
    }

    public SocketChannel getSocket(Session session) {
        for ( SocketChannel h : channelMap.keySet()) {
            if ( channelMap.get(h).equals(session)){
                return h;
            }
        }
        return null;
    }
}
