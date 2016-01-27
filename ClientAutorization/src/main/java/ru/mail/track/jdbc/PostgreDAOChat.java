package ru.mail.track.jdbc;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.Chat;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PostgreDAOChat implements DAOChat {

    private final Connection connection;

    public PostgreDAOChat(Connection connection) { this.connection = connection;}

    @Override
    public void addChat(List<Long> usersId) throws SQLException {

        if (!isChatExist(usersId)) {

            Statement stmt = null;
            stmt = connection.createStatement();

            String sqlMaxId = "SELECT MAX(id) FROM \"chat\"";

            ResultSet res = stmt.executeQuery(sqlMaxId);

            Long maxId = null;

            try {
                res.next();
                maxId = res.getLong(1) + 1L;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                res.close();
            }

            try {

                for (int i = 0; i < usersId.size(); i++) {
                    String sql = "INSERT INTO \"chat_user\" (ID_CHAT,ID_SENDER)"
                            + "VALUES (" + maxId + "," + usersId.get(i) + ");";
                    stmt.executeUpdate(sql);
                }

                String sql = "INSERT INTO \"chat\" (ID)"
                        + "VALUES (" + maxId + ");";
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stmt.close();
            }
        }
    }

    @Override
    public boolean isChatExist(List<Long> usersId) throws SQLException {

        Statement stmtChat = null;
        stmtChat = connection.createStatement();

        String sql = "SELECT * FROM \"chat\";";
        ResultSet resChat = null;
        resChat = stmtChat.executeQuery(sql);

        List<Long> users = new ArrayList<>();
        try {
            while (resChat.next()) {

                users.clear();

                Long idChat = resChat.getLong("id");

                Statement stmtChatUser = null;
                stmtChatUser = connection.createStatement();

                String sqlChatUser = "SELECT * FROM \"chat_user\" WHERE id_chat ='" + idChat + "';";
                ResultSet resChatUser = stmtChatUser.executeQuery(sqlChatUser);

                try {

                    while (resChatUser.next()) {
                        users.add(resChatUser.getLong("id_sender"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    stmtChatUser.close();
                    resChatUser.close();
                }

                if (users.equals(usersId)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            resChat.close();
            stmtChat.close();
        }

        return false;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) throws SQLException {

        Statement stmt = null;
        stmt = connection.createStatement();

        String sql = "SELECT * FROM \"chat_user\" WHERE id_sender ='" + userId + "';";
        ResultSet res = null;
        res = stmt.executeQuery(sql);

        List<Long> chats = new ArrayList<>();
        try {
            while (res.next()) {
                chats.add(res.getLong("id_chat"));
            }
            return chats;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            res.close();
        }

        return null;
    }

    @Override
    public Chat getChatById(Long chatId) throws SQLException {

        Statement stmt = null;
        stmt = connection.createStatement();

        String sql = "SELECT * FROM \"chat_user\" WHERE id_chat ='" + chatId + "';";
        ResultSet res = null;
        res = stmt.executeQuery(sql);

        List<Long> users = new ArrayList<>();
        try {
            while (res.next()) {
                users.add(res.getLong("id_sender"));
            }
            Chat chat = new Chat();
            chat.setId(chatId);
            chat.setParticipantIds(users);
            return chat;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            res.close();
        }

        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) throws SQLException {

        Statement stmt = null;
        stmt = connection.createStatement();
        String sql = "SELECT * FROM \"message\" WHERE id_chat ='" + chatId + "';";
        ResultSet res = stmt.executeQuery(sql);

        List<Long> messagesId = new ArrayList<>();
        try {
            while (res.next()) {
                messagesId.add(res.getLong("id"));
            }
            return messagesId;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            res.close();
        }

        return null;
    }

    @Override
    public void addMessage(Long chatId, Message message) throws SQLException {

        Statement stmtMaxId = null;
        stmtMaxId = connection.createStatement();
        String sqlMaxId = "SELECT MAX(id) FROM \"message\"";
        ResultSet resMaxId = stmtMaxId.executeQuery(sqlMaxId);

        Long maxId = null;

        try {
            if(resMaxId.next()) {
                maxId = resMaxId.getLong(1) + 1L;
            } else {
                maxId = 0L;
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        } finally {
            resMaxId.close();
            stmtMaxId.close();
        }

        Statement stmt = null;
        stmt = connection.createStatement();
        SendMessage sendMsg = (SendMessage)message;
        String sql = "INSERT INTO \"message\" (MESSAGE,ID,ID_CHAT)"
                + "VALUES ('" + sendMsg.getMessage() + "'," + maxId + ","
                + chatId + ");";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }

    @Override
    public Message getMessageById(Long messageId) throws SQLException {
        Statement stmt = null;
        stmt = connection.createStatement();
        String sql = "SELECT * FROM \"message\" WHERE id ='" + messageId + "';";
        ResultSet res = stmt.executeQuery(sql);

        try {
            res.next();
            SendMessage sendMsg = new SendMessage();
            sendMsg.setMessage(res.getString("message"));
            sendMsg.setChatId(res.getLong("id_chat"));
            sendMsg.setType(CommandType.MSG_SEND);
            return sendMsg;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            res.close();
            stmt.close();
        }

        return null;
    }

}
