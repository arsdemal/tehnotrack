package ru.mail.track.jdbc;

import ru.mail.track.message.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class PostgreDAOUser implements DAOUser {

    private final Connection connection;

    public PostgreDAOUser(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User addUser(User user) {

        Statement stmt = null;
        ResultSet res = null;
        Long maxId= null;
        try {
            stmt = connection.createStatement();
            String sqlMaxId = "SELECT MAX(id) FROM \"user\";";
            res = stmt.executeQuery(sqlMaxId);
            res.next();
            maxId = res.getLong(1) + 1L;
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO \"user\" (ID,LOGIN,PASSWORD) "
                + "VALUES (" + maxId + ",'" + user.getName()
                + "','" + user.getPass() + "');";

        try {
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean deleteUser() {
        return false;
    }

    @Override
    public User getUser(String login, String pass) {

        Statement stmt = null;
        ResultSet res = null;
        User user = null;

        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM \"user\" WHERE login = '" + login + "' AND password = '" + pass + "';";
            res = stmt.executeQuery(sql);
            while (res.next()) {
                Long id = res.getLong("id");
                user = new User(login, pass);
                user.setId(id);
            }

            res.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public boolean updateUser() {
        return false;
    }

    @Override
    public boolean isUserExist(String login){

        Statement stmt = null;
        ResultSet res = null;

        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM \"user\" WHERE login = '" + login + "';";
            res = stmt.executeQuery(sql);
            while (res.next()) {
                return true;
            }
            res.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Long getUserById() {
        return null;
    }
}
