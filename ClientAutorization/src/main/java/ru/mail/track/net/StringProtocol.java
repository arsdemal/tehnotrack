package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.CommandType;
import ru.mail.track.message.*;

/**
 *
 */
//@AutoComponent
public class StringProtocol implements Protocol {

    static Logger log = LoggerFactory.getLogger(StringProtocol.class);

    public static final String DELIMITER = ";";

    @Override
    public Message decode(byte[] bytes) {
        String str = new String(bytes);
        log.info("decoded: {}", str);
        String[] tokens = str.split(DELIMITER);
        CommandType type = CommandType.valueOf(tokens[0]);
        switch (type) {
            case USER_LOGIN:
                LoginMessage loginMessage = new LoginMessage();
                loginMessage.setLogin(tokens[1]);
                loginMessage.setPass(tokens[2]);
                return loginMessage;
            case MSG_SEND:
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(Long.valueOf(tokens[1]));
                sendMessage.setMessage(tokens[2]);
                return sendMessage;
            case USER_HELP:
                return new HelpMessage();
            case MSG_INFO:
                InfoMessage infoMessage = new InfoMessage();
                infoMessage.setInfo(tokens[1]);
                return infoMessage;
            case CHAT_LIST:
                return new ChatListMessage();
            case USER_NICK:
                UserMessage userMessage = new UserMessage();
                userMessage.setUserName(tokens[1]);
                return userMessage;
            case USER_INF0:
                return new UserInfoMessage();
            case USER_PASS:
                UserPassMessage passMsg = new UserPassMessage();
                passMsg.setOldPass(tokens[1]);
                passMsg.setNewPass(tokens[2]);
                return passMsg;
            default:
                throw new RuntimeException("Invalid type: " + type);
        }
    }

    @Override
    public byte[] encode(Message msg) {
        StringBuilder builder = new StringBuilder();
        CommandType type = msg.getType();
        builder.append(type).append(DELIMITER);
        switch (type) {
            case USER_LOGIN:
                LoginMessage loginMessage = (LoginMessage) msg;
                builder.append(loginMessage.getLogin()).append(DELIMITER);
                builder.append(loginMessage.getPass()).append(DELIMITER);
                break;
            case MSG_SEND:
                SendMessage sendMessage = (SendMessage) msg;
                builder.append(sendMessage.getChatId()).append(DELIMITER);
                builder.append(sendMessage.getMessage()).append(DELIMITER);
                break;
            case USER_HELP:
                break;
            case MSG_INFO:
                InfoMessage infoMessage = (InfoMessage) msg;
                builder.append(infoMessage.getInfo()).append(DELIMITER);
                break;
            case CHAT_LIST:
                break;
            case USER_NICK:
                UserMessage userMessage = (UserMessage) msg;
                builder.append(userMessage.getUserName()).append(DELIMITER);
                break;
            case USER_INF0:
                break;
            case USER_PASS:
                UserPassMessage userPassMessage = (UserPassMessage) msg;
                builder.append(userPassMessage.getOldPass()).append(DELIMITER);
                builder.append(userPassMessage.getNewPass()).append(DELIMITER);
                break;
            default:
                throw new RuntimeException("Invalid type: " + type);


        }
        log.info("encoded: {}", builder.toString());
        return builder.toString().getBytes();
    }



}