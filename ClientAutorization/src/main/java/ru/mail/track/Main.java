package ru.mail.track;


import ru.mail.track.jdbc.DAOUser;
import ru.mail.track.jdbc.PostgreDAOFactory;
import ru.mail.track.message.User;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        PostgreDAOFactory factory = new PostgreDAOFactory();
        Connection connection = factory.createConnection();
        DAOUser daoUser = factory.getUserDAO(connection);

        User user0 = new User("ars","123");
        user0.setId(0L);

        User user1 = new User("dem","123");
        user1.setId(1L);

        daoUser.addUser(user0);
        daoUser.addUser(user1);

        User user2 = daoUser.findUser("ars");
        System.out.println(user2.getPass());
    }


}