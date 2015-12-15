package ru.mail.track.jdbc;

import java.sql.Connection;

/**
 *
 */
public abstract class DAOFactory {
    public static final int ORACLE = 2;

    public abstract ChatDAO getChatDAO(Connection connection);
    public abstract DAOUser getUserDAO(Connection connection);
    public abstract MessageDAO getMessageDAO(Connection connection);

    public static DAOFactory getDAOFactory() {
        return new PostgreDAOFactory();
    }
}
