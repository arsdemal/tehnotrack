package ru.mail.track.net;


import ru.mail.track.message.Message;

import java.io.IOException;
import java.net.ProtocolException;

/**
 *
 */
public interface Protocol {

    byte[] encode(Message msg) throws IOException;

    Message decode(byte[] data) throws ProtocolException;
}