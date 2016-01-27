package ru.mail.track.net;

import ru.mail.track.message.Message;

import java.io.*;

/**
 *
 */
public class SerializeProtocol implements Protocol {
    @Override
    public byte[] encode(Message msg) throws IOException {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(msg);
            oos.flush();
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oos.close();
            baos.close();
        }
        return null;
    }

    @Override
    public Message decode(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
