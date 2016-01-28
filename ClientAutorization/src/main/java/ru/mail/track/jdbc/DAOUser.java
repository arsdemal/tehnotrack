package ru.mail.track.jdbc;

import ru.mail.track.message.User;
import ru.mail.track.message.UserStore;

/**
 *
 */
public interface DAOUser extends UserStore {

    User addUser(User user);

    boolean deleteUser();

    User getUser(String login, String pass);

    boolean updateUser();

    boolean isUserExist(String login);

    Long getUserById();

}
