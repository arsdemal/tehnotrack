package ru.mail.track.net.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.CommandType;
import ru.mail.track.message.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class NioInputHandler {

    static Logger log = LoggerFactory.getLogger(NioInputHandler.class);


    public Message processInput(String line) throws IOException {
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
                    return loginMessage;
                } else { // регистрируемся

                    log.info("Enter your login password");

                    RegisterMessage regMsg = new RegisterMessage();
                    regMsg.setType(CommandType.USER_REG);

                    Scanner scanner = new Scanner(System.in);
                    String[] regTokens = scanner.nextLine().split(" ");

                    if ( regTokens.length != 2) {
                        log.info("Incorrect data");
                    } else {
                        regMsg.setLogin(regTokens[0]);
                        regMsg.setPass(regTokens[1]);
                        return regMsg;
                    }
                }
                break;
            case "send":
                if (tokens.length != 3) {
                    log.info("Invalid input");
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setType(CommandType.MSG_SEND);
                    sendMessage.setChatId(Long.valueOf(tokens[1]));
                    sendMessage.setMessage(tokens[2]);
                    return sendMessage;
                }
            case "help":
                HelpMessage helpMessage = new HelpMessage();
                helpMessage.setType(CommandType.USER_HELP);
                return helpMessage;
            case "chat_list":
                ChatListMessage chatListMessage = new ChatListMessage();
                chatListMessage.setType(CommandType.CHAT_LIST);
                return chatListMessage;
            case "user":
                if (tokens.length != 2) {
                    log.info("Invalid input");
                } else {
                    UserMessage userMessage = new UserMessage();
                    userMessage.setType(CommandType.USER_NICK);
                    userMessage.setUserName(tokens[1]);
                    return userMessage;
                }
            case "user_info":
                UserInfoMessage infoMsg = new UserInfoMessage();
                infoMsg.setType(CommandType.USER_INF0);
                return infoMsg;
            case "user_pass":
                if (tokens.length != 3) {
                    log.info("Invalid input");
                } else {
                    UserPassMessage passMsg = new UserPassMessage();
                    passMsg.setType(CommandType.USER_PASS);
                    passMsg.setOldPass(tokens[1]);
                    passMsg.setNewPass(tokens[2]);
                    return passMsg;
                }
            case "chat_create":
                if (tokens.length < 2) {
                    log.info("Invalid input");
                } else {
                    ChatCreateMessage chatCreateMsg = new ChatCreateMessage();
                    chatCreateMsg.setType(CommandType.CHAT_CREATE);
                    List<Long> usersId = new ArrayList<>();
                    for (int i = 1; i < tokens.length; i++) {
                        usersId.add(Long.parseLong(tokens[i]));
                    }
                    chatCreateMsg.setUsersId(usersId);
                    return chatCreateMsg;
                }

            case "chat_find":
                if (tokens.length != 3) {
                    log.info("Invalid input");
                } else {
                    ChatFindMessage findMessage = new ChatFindMessage();
                    findMessage.setType(CommandType.CHAT_FIND);
                    findMessage.setChatId(Long.parseLong(tokens[1]));
                    findMessage.setRegex(tokens[2]);
                    return findMessage;
                }
            case "chat_history":
                if (tokens.length != 2) {
                    log.info("Invalid input");
                } else {
                    ChatHistoryMessage historyMessage = new ChatHistoryMessage();
                    historyMessage.setType(CommandType.CHAT_HISTORY);
                    historyMessage.setChatId(Long.parseLong(tokens[1]));
                    return historyMessage;
                }
            default:
                System.out.println("Invalid input: " + line);
        }

        return null;
    }
}
