package ru.mail.track.net;

import org.apache.commons.lang.SerializationUtils;
import ru.mail.track.message.Message;

import java.net.ProtocolException;

public class ApacheProtocol implements Protocol {
    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        return (Message) SerializationUtils.deserialize(bytes);
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        return SerializationUtils.serialize(msg);
    }
}