package ru.mail.track.jdbc;

import java.sql.Connection;

/**
 *
 */
public abstract class DAOFactory {
    public static final int ORACLE = 2;

    public abstract DAOChat getChatDAO(Connection connection);
    public abstract DAOUser getUserDAO(Connection connection);
    public abstract DAOMessage getMessageDAO(Connection connection);

    public static DAOFactory getDAOFactory() {
        return new PostgreDAOFactory();
    }
}
