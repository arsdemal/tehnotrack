package ru.mail.track.jdbc;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

/**
 *
 */
public interface DAOMessage {

    void addMessage(Message msg, Session session);

    Message getMessage(Long id, Session session);

    boolean updateMessage();


}
