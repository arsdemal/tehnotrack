package ru.mail.track;


import ru.mail.track.commands.*;
import ru.mail.track.jdbc.DAOChat;
import ru.mail.track.jdbc.DAOUser;
import ru.mail.track.jdbc.PostgreDAOFactory;
import ru.mail.track.message.MessageStore;
import ru.mail.track.message.MessageStoreStub;
import ru.mail.track.message.UserStore;
import ru.mail.track.message.UserStoreStub;
import ru.mail.track.net.ApacheProtocol;
import ru.mail.track.net.Protocol;
import ru.mail.track.net.SessionManager;
import ru.mail.track.net.nio.NioServer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {


        // Предустановки для серверов
        Protocol protocol = new ApacheProtocol();
        SessionManager sessionManager = new SessionManager();

        PostgreDAOFactory factory = new PostgreDAOFactory();
        Connection connection = factory.createConnection();
        DAOUser daoUser = factory.getUserDAO(connection);
        DAOChat daoChat = factory.getChatDAO(connection);

        UserStore userStore = new UserStoreStub(daoUser);
        MessageStore messageStore = new MessageStoreStub(daoChat);

        Map<CommandType, Command> cmds = new HashMap<>();
        cmds.put(CommandType.USER_LOGIN, new LoginCommand(userStore, sessionManager));
        cmds.put(CommandType.MSG_SEND, new SendCommand(sessionManager, messageStore));
        cmds.put(CommandType.USER_HELP, new HelpCommand(cmds));
        cmds.put(CommandType.CHAT_LIST, new ChatListCommand(messageStore));
        cmds.put(CommandType.USER_NICK, new UserCommand(userStore));
        cmds.put(CommandType.USER_INF0, new UserInfoCommand());
        cmds.put(CommandType.USER_PASS, new UserPassCommand());
        cmds.put(CommandType.USER_REG, new RegisterCommand(userStore,sessionManager));
        cmds.put(CommandType.CHAT_CREATE, new ChatCreateCommand(messageStore));
        cmds.put(CommandType.CHAT_HISTORY, new ChatHistoryCommand(messageStore));
        cmds.put(CommandType.CHAT_FIND, new ChatFindCommand(messageStore));

        CommandHandler handler = new CommandHandler(cmds);

        Thread t = new Thread(new NioServer(protocol,sessionManager,handler));
        t.start();

    }

}