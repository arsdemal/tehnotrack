package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.CommandType;
import ru.mail.track.message.*;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

        log.debug("onMessage: {} type {}", message, type);
        switch (type) {
            case MSG_INFO:
                InfoMessage infoMsg = (InfoMessage) message;
                List<String> infoList = infoMsg.getInfo();
                infoList.forEach(log::info);
                break;
            case MSG_SEND:
                SendMessage sendMsg = (SendMessage) message;
                System.out.println(sendMsg.getMessage());
                break;
        }
    }

    public void processInput(String line) throws IOException {
        String[] tokens = line.split(" ");
        log.debug("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];

        //обрабатываем информацию с консоли
        switch (cmdType) {
            case "login":
                if (tokens.length == 3) { // логинимся
                    LoginMessage loginMessage = new LoginMessage();
                    loginMessage.setType(CommandType.USER_LOGIN);
                    loginMessage.setLogin(tokens[1]);
                    loginMessage.setPass(tokens[2]);
                    session.getConnectionHandler().send(loginMessage);
                } else { // регистрируемся

                    log.info("Enter your login password");

                    RegisterMessage regMsg = new RegisterMessage();
                    regMsg.setType(CommandType.USER_REG);

                    Scanner scanner = new Scanner(System.in);
                    String[] regTokens = scanner.nextLine().split(" ");

                    if ( regTokens.length != 2) {
                        log.info("Incorrect data");
                    } else {
                        try {
                            regMsg.setLogin(regTokens[0]);
                            regMsg.setPass(regTokens[1]);
                            session.getConnectionHandler().send(regMsg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
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
            case "user":
                UserMessage userMessage = new UserMessage();
                userMessage.setType(CommandType.USER_NICK);
                userMessage.setUserName(tokens[1]);
                session.getConnectionHandler().send(userMessage);
                break;
            case "user_info":
                UserInfoMessage infoMsg = new UserInfoMessage();
                infoMsg.setType(CommandType.USER_INF0);
                session.getConnectionHandler().send(infoMsg);
                break;
            case "user_pass":
                UserPassMessage passMsg = new UserPassMessage();
                passMsg.setType(CommandType.USER_PASS);
                passMsg.setOldPass(tokens[1]);
                passMsg.setNewPass(tokens[2]);
                session.getConnectionHandler().send(passMsg);
                break;
            case "chat_create":
                ChatCreateMessage chatCreateMsg = new ChatCreateMessage();
                chatCreateMsg.setType(CommandType.CHAT_CREATE);
                List<Long> usersId = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    usersId.add(Long.parseLong(tokens[i]));
                }
                chatCreateMsg.setUsersId(usersId);
                session.getConnectionHandler().send(chatCreateMsg);
                break;
            case "chat_find":
                ChatFindMessage findMessage = new ChatFindMessage();
                findMessage.setType(CommandType.CHAT_FIND);
                findMessage.setChatId(Long.parseLong(tokens[1]));
                findMessage.setRegex(tokens[2]);
                session.getConnectionHandler().send(findMessage);
                break;
            case "chat_history":
                ChatHistoryMessage historyMessage = new ChatHistoryMessage();
                historyMessage.setType(CommandType.CHAT_HISTORY);
                historyMessage.setChatId(Long.parseLong(tokens[1]));
                session.getConnectionHandler().send(historyMessage);
                break;
            default:
                System.out.println("Invalid input: " + line);
        }
    }
}
