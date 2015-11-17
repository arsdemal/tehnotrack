package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.CommandType;
import ru.mail.track.message.*;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.Arrays;

/**
    1) формирует сообщения и отдает их connectionHandler
    2) берет сообщение от connectionHandler и выводит информацию клиенту
*/
public class InputHandler implements MessageListener {

    private Session session;
    static Logger log = LoggerFactory.getLogger(InputHandler.class);

    public InputHandler(Session session) {
        this.session = session;
    }

    @Override
    public void onMessage(Session session, Message message) {
        CommandType type = message.getType();
        log.info("onMessage: {} type {}", message, type);
        switch (type) {
            case MSG_INFO:
                InfoMessage infoMsg = (InfoMessage) message;
                log.info(infoMsg.getInfo());
                break;
            case MSG_SEND:
                break;
        }
    }

    public void processInput(String line) throws IOException {
        String[] tokens = line.split(" ");
        log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];

        //обрабатываем информацию с консоли
        switch (cmdType) {
            case "login":
                //создаем LoginMessage и отдаем хендлеру
                LoginMessage loginMessage = new LoginMessage();
                loginMessage.setType(CommandType.USER_LOGIN);
                if (tokens.length == 3) {
                    loginMessage.setLogin(tokens[1]);
                    loginMessage.setPass(tokens[2]);
                }
                session.getConnectionHandler().send(loginMessage);
                break;
            case "send":
                SendMessage sendMessage = new SendMessage();
                sendMessage.setType(CommandType.MSG_SEND);
                sendMessage.setChatId(Long.valueOf(tokens[1]));
                sendMessage.setMessage(tokens[2]);
                session.getConnectionHandler().send(sendMessage);
                break;
            case "help":
                HelpMessage helpMessage = new HelpMessage();
                helpMessage.setType(CommandType.USER_HELP);
                session.getConnectionHandler().send(helpMessage);
                break;
            case "chat_list":
                ChatListMessage chatListMessage = new ChatListMessage();
                chatListMessage.setType(CommandType.CHAT_LIST);
                session.getConnectionHandler().send(chatListMessage);
                break;
            default:
                System.out.println("Invalid input: " + line);
        }
    }
}
