package ru.mail.track.net;

import ru.mail.track.message.Message;

import java.io.IOException;

/**
 * Обработчик сокета
 */
public interface ConnectionHandler extends Runnable {

    void send(Message msg) throws IOException;

    void addListener(MessageListener listener);

    void stop();
}