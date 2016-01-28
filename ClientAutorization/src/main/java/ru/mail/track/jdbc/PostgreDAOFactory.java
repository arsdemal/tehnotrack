package ru.mail.track.jdbc;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class PostgreDAOFactory extends DAOFactory {

    public static Connection createConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("My DB");
        source.setServerName("178.62.140.149");
        source.setDatabaseName("arsdemal");
        source.setUser("senthil");
        source.setPassword("ubuntu");
        source.setMaxConnections(10);

        try {
            return source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DAOMessageStore getDAOMessageStore(Connection connection) {
        return new PostgreDAOMessageStore(connection);
    }

    @Override
    public DAOUser getDAOUser(Connection connection) {
        return new PostgreDAOUser(connection);
    }

}
