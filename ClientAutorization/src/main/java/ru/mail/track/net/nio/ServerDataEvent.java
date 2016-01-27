package ru.mail.track.net.nio;

/**
 *
 */
import java.nio.channels.SocketChannel;

class ServerDataEvent {
    public NewNioServer server;
    public SocketChannel socket;
    public byte[] data;

    public ServerDataEvent(NewNioServer server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;
    }
}