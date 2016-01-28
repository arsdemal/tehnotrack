package ru.mail.track.net.nio;

import ru.mail.track.session.Session;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ChannelManager {
    private Map<SocketChannel, Session> sessionMap;
    private Map<Session, SocketChannel> channelMap;

    public ChannelManager() {
        channelMap = new HashMap<>();
        sessionMap = new HashMap<>();
    }

    public void addChannel(SocketChannel socketChannel, Session session) {
        channelMap.put(session, socketChannel);
        sessionMap.put(socketChannel, session);
    }

    public Session getSession(SocketChannel socketChannel) {
        Session session = sessionMap.get(socketChannel);
        return session;
    }

    public SocketChannel getSocket(Session session) {
        SocketChannel socketChannel = channelMap.get(session);
        return socketChannel;
    }
}
