package ru.mail.track.Test.integr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.track.Main;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.net.nio.NioClient;
import ru.mail.track.session.Session;

/**
 *
 */
public class IntegrTest {

    private NioClient client;
    private String result;

    @Before
    public void setup() throws Exception{
        new Thread(()-> {
            try {
                Main.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        client = new NioClient();
        new Thread(()-> {
            try {
                client.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void login() throws Exception {
        assert(gotResult(new SendMessage(-1L, "Login Failed"),"login A 11"));
    }

    private boolean gotResult(Object result, String on) throws Exception {
        client.writeToChannel(client.processInput(on));
        Thread.sleep(100);
        return this.result.equals(result.toString());
    }

    public void onMessage(Session session, Message message) {
        result = message.toString();
//        System.out.println(result);
    }

    @After
    public void close() {
        //todo
    }

}
