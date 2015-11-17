package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.session.Session;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//import ru.mail.track.reflection.di.Auto;


public class ThreadedClient {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";
    static Logger log = LoggerFactory.getLogger(ThreadedClient.class);
    ConnectionHandler handler;
    InputHandler inputHandler;

    //@Auto(isRequired = true)
    private Protocol protocol = new StringProtocol();

    public ThreadedClient() {
    }

    @PostConstruct
    public void init() {
        try {
            Socket socket = new Socket(HOST, PORT);
            Session session = new Session();
            // создаем хендлер для перехвата и отправки собщений для inputHandler
            handler = new SocketConnectionHandler(protocol, session, socket);
            inputHandler = new InputHandler(session);
            // слушаем наш хендлер
            handler.addListener(inputHandler);

            // запускаем новый поток хендлер
            Thread socketHandler = new Thread(handler);
            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            // exit, failed to open socket
        }
    }

    public static void main(String[] args) throws Exception {
        Protocol protocol = new StringProtocol();
        ThreadedClient client = new ThreadedClient();

        client.init(); // включили клиент

        // вводим иформацию в консоль
        Scanner scanner = new Scanner(System.in);
        System.out.println("$");
        while (true) {
            String input = scanner.nextLine();
            if ("q".equals(input)) {
                return;
            }
            client.inputHandler.processInput(input);
        }
    }


}