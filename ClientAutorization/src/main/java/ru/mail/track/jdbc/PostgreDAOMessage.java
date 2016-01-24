package ru.mail.track.jdbc;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

/**
 *
 */
public class PostgreDAOMessage implements DAOMessage {

    @Override
    public void addMessage(Message msg, Session session) {

    }

    @Override
    public Message getMessage(Long id, Session session) {
        return null;
    }

    @Override
    public boolean updateMessage() {
        return false;
    }
}
