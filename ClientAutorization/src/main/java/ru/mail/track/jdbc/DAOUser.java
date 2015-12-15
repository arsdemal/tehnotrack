package ru.mail.track.jdbc;

import ru.mail.track.message.User;

import java.sql.SQLException;

/**
 *
 */
public interface DAOUser {

    void addUser(User user) throws SQLException;

    boolean deleteUser();

    User getUser(String login, String pass) throws SQLException;

    boolean updateUser();

    boolean isUserExist(String login) throws SQLException;

    Long getUserById();

}
