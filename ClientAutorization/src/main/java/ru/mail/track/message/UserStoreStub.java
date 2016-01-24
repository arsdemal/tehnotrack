package ru.mail.track.message;

import ru.mail.track.jdbc.DAOUser;

import java.sql.SQLException;

/**
 *
 */
public class UserStoreStub implements UserStore {

    private DAOUser users;

    public UserStoreStub(DAOUser users){
        this.users = users;
    }

    @Override
    public User addUser(User user) {
        try {
            users.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUser(String login, String pass) {
        try {
            return users.getUser(login, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public boolean isUserExist(String name) {
        try {
            return users.isUserExist(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}