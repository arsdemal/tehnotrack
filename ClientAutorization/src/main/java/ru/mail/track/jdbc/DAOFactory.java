package ru.mail.track.jdbc;

import java.sql.Connection;

/**
 *
 */
public abstract class DAOFactory {
    public static final int ORACLE = 2;

    public abstract DAOMessageStore getDAOMessageStore(Connection connection);
    public abstract DAOUser getDAOUser(Connection connection);

    public static DAOFactory getDAOFactory() {
        return new PostgreDAOFactory();
    }
}
