package ru.mail.track.jdbc;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class PostgreDAOFactory extends DAOFactory {

    public static final String DRIVER=
            "COM.cloudscape.core.RmiJdbcDriver";
    public static final String DBURL=
            "jdbc:cloudscape:rmi://localhost:1099/CoreJ2EEDB";

    public static Connection createConnection() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("My DB");
        source.setServerName("178.62.140.149");
        source.setDatabaseName("arsdemal");
        source.setUser("senthil");
        source.setPassword("ubuntu");
        source.setMaxConnections(10);

        return source.getConnection();
    }

    @Override
    public ChatDAO getChatDAO(Connection connection) {
        return null;
    }

    @Override
    public DAOUser getUserDAO(Connection connection) {
        return new PostgreDAOUser(connection);
    }

    @Override
    public MessageDAO getMessageDAO(Connection connection) {
        return null;
    }
}
