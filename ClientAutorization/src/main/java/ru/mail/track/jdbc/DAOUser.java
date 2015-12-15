package ru.mail.track.jdbc;

import ru.mail.track.message.User;

import java.sql.SQLException;

/**
 *
 */
public interface DAOUser {

    int addUser(User user) throws SQLException;

    boolean deleteUser();

    User findUser(String login) throws SQLException;

    boolean updateUser();

    boolean isUserExist();

    Long getUserById();

}
