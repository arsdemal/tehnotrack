package ru.mail.track.Test;

import org.junit.Before;
import org.junit.Test;
import ru.mail.track.commands.CommandType;
import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.net.Protocol;
import ru.mail.track.net.SerializeProtocol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SerializeProtocolTest {

    private Message message;
    private Protocol protocol;

    @Before
    public void setString() throws Exception {

        message = new InfoMessage();
        InfoMessage infoMessage = (InfoMessage) message;
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> list = new ArrayList<>();
        list.add(new String("Hello!"));
        infoMessage.setInfo(list);
        protocol = new SerializeProtocol();
    }

    @Test
    public void testSerialize() throws Exception {
        byte[] array = protocol.encode(message);
        Message msg = protocol.decode(array);
        InfoMessage infoMessage = (InfoMessage)message;
        InfoMessage infoMessage1 = (InfoMessage) msg;
        assert infoMessage.getInfo().equals(infoMessage1.getInfo());
    }

}