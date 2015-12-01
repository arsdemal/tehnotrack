package ru.mail.track.net;


import ru.mail.track.message.Message;

/**
 *
 */
public interface Protocol {

    byte[] encode(Message msg);

    Message decode(byte[] data);
}