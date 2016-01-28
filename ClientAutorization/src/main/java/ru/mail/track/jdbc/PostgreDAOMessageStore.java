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
public class PostgreDAOMessageStore implements DAOMessageStore {

    private final Connection connection;

    public PostgreDAOMessageStore(Connection connection) { this.connection = connection;}

    @Override
    public void addChat(List<Long> usersId) {
        if (!isChatExist(usersId)) {

            Statement stmt = null;
            ResultSet res = null;
            Long maxId = null;

            try {
                stmt = connection.createStatement();
                String sqlMaxId = "SELECT MAX(id) FROM \"chat\"";
                res = stmt.executeQuery(sqlMaxId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                res.next();
                maxId = res.getLong(1) + 1L;
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
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
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isChatExist(List<Long> usersId){
        Statement stmtChat = null;
        ResultSet resChat = null;
        try {
            stmtChat = connection.createStatement();
            String sql = "SELECT * FROM \"chat\";";
            resChat = stmtChat.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            try {
                resChat.close();
                stmtChat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId){

        ResultSet res = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM \"chat_user\" WHERE id_sender ='" + userId + "';";
            res = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Long> chats = new ArrayList<>();
        try {
            while (res.next()) {
                chats.add(res.getLong("id_chat"));
            }
            stmt.close();
            res.close();
            return chats;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Chat getChatById(Long chatId){

        Statement stmt = null;
        ResultSet res = null;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM \"chat_user\" WHERE id_chat ='" + chatId + "';";
            res = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Long> users = new ArrayList<>();
        try {
            while (res.next()) {
                users.add(res.getLong("id_sender"));
            }
            Chat chat = new Chat();
            chat.setId(chatId);
            chat.setParticipantIds(users);
            stmt.close();
            res.close();
            return chat;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId){

        Statement stmt = null;
        ResultSet res = null;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM \"message\" WHERE id_chat ='" + chatId + "';";
            res = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Long> messagesId = new ArrayList<>();
        try {
            while (res.next()) {
                messagesId.add(res.getLong("id"));
            }
            stmt.close();
            res.close();
            return messagesId;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addMessage(Long chatId, Message message){

        Statement stmtMaxId = null;
        ResultSet resMaxId = null;
        try {
            stmtMaxId = connection.createStatement();
            String sqlMaxId = "SELECT MAX(id) FROM \"message\"";
            resMaxId = stmtMaxId.executeQuery(sqlMaxId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Long maxId = null;

        try {
            if(resMaxId.next()) {
                maxId = resMaxId.getLong(1) + 1L;
            } else {
                maxId = 0L;
            }
            resMaxId.close();
            stmtMaxId.close();
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement stmt = null;
            stmt = connection.createStatement();
            SendMessage sendMsg = (SendMessage)message;
            String sql = "INSERT INTO \"message\" (MESSAGE,ID,ID_CHAT)"
                    + "VALUES ('" + sendMsg.getMessage() + "'," + maxId + ","
                    + chatId + ");";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {

    }

    @Override
    public void createChat(List<Long> usersId) {

    }

    @Override
    public Message getMessageById(Long messageId){
        Statement stmt = null;
        ResultSet res = null;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM \"message\" WHERE id ='" + messageId + "';";
            res = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            res.next();
            SendMessage sendMsg = new SendMessage();
            sendMsg.setMessage(res.getString("message"));
            sendMsg.setChatId(res.getLong("id_chat"));
            sendMsg.setType(CommandType.MSG_SEND);
            res.close();
            stmt.close();
            return sendMsg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
