package ru.mail.track.net.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.net.ConnectionHandler;
import ru.mail.track.net.MessageListener;
import ru.mail.track.net.Protocol;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  Класс для работы с CommandHandler
 *
 */
public class NioServerConnectionHandler implements ConnectionHandler,MessageListener {


    static Logger log = LoggerFactory.getLogger(NioServerConnectionHandler.class);

    // подписчики
    private List<MessageListener> listeners = new ArrayList<>();
    private Protocol protocol;
    private Session session;


    @Override
    public void send(Message msg) throws IOException {

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

    }

    @Override
    public void onMessage(Session session, Message message) {

    }
}
