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
    public void addUser(User user) throws SQLException {

        Statement stmt = null;
        stmt = connection.createStatement();

        String sqlMaxId = "SELECT MAX(id) FROM \"user\";";

        ResultSet res = stmt.executeQuery(sqlMaxId);

        res.next();

        Long maxId = res.getLong(1) + 1L;

        res.close();

        String sql = "INSERT INTO \"user\" (ID,LOGIN,PASSWORD) "
                + "VALUES (" + maxId + ",'" + user.getName()
                + "','" + user.getPass() + "');";

        stmt.executeUpdate(sql);
        stmt.close();

        /*try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        
    }

    @Override
    public boolean deleteUser() {
        return false;
    }

    @Override
    public User getUser(String login, String pass) throws SQLException {
        Statement stmt = connection.createStatement();

        String sql = "SELECT * FROM \"user\" WHERE login = '"+ login + "' AND password = '"+ pass + "';";

        ResultSet res = stmt.executeQuery(sql);

        User user = null;

        while (res.next()) {
            Long id = res.getLong("id");
            user = new User(login,pass);
            user.setId(id);
        }

        res.close();
        stmt.close();

        return user;
    }

    @Override
    public boolean updateUser() {
        return false;
    }

    @Override
    public boolean isUserExist(String login) throws SQLException {

        Statement stmt = connection.createStatement();

        String sql = "SELECT * FROM \"user\" WHERE login = '"+ login + "';";
        ResultSet res = stmt.executeQuery(sql);

        while (res.next()) {
            return true;
        }

        res.close();
        stmt.close();

        return false;
    }

    @Override
    public Long getUserById() {
        return null;
    }
}
