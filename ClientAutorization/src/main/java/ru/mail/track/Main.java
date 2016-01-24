package ru.mail.track;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.jdbc.DAOChat;
import ru.mail.track.jdbc.PostgreDAOFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        PostgreDAOFactory factory = new PostgreDAOFactory();
        Connection connection = factory.createConnection();
        DAOChat daoChat = factory.getChatDAO(connection);

        List<Long> users = new ArrayList<>();
        users.add(1L);
        users.add(2L);
        users.add(3L);

        List<Long> users2 = new ArrayList<>();
        users2.add(1L);
        users2.add(2L);

        try {
            daoChat.addChat(users);
            daoChat.addChat(users2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if ( daoChat.isChatExist(users)) {
                log.info("true");
            } else {
                log.info("false");
            }
            if ( daoChat.isChatExist(users2)) {
                log.info("true");
            } else {
                log.info("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}