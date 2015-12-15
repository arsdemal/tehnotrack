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
    public int addUser(User user) throws SQLException {

        Statement stmt = null;
        stmt = connection.createStatement();

        String sql = "INSERT INTO \"user\" (ID,LOGIN,PASSWORD) "
                + "VALUES (" + user.getId() + ",'" + user.getName()
                + "','" + user.getPass() + "');";

        stmt.executeUpdate(sql);
        stmt.close();

        /*try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        return 0;
    }

    @Override
    public boolean deleteUser() {
        return false;
    }

    @Override
    public User findUser(String login) throws SQLException {
        Statement stmt = connection.createStatement();

        String sql = "SELECT * FROM \"user\" WHERE login = '"+ login + "';"; // and password ='" + user.getPass() + "'";

        ResultSet res = stmt.executeQuery(sql);

        User user = null;

        while (res.next()) {
            Long id = res.getLong("id");
            String pass = res.getString("password");
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
    public boolean isUserExist() {
        return false;
    }

    @Override
    public Long getUserById() {
        return null;
    }
}
