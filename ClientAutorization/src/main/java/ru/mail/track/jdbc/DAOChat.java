package ru.mail.track.jdbc;

import ru.mail.track.message.Chat;
import ru.mail.track.message.Message;

import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public interface DAOChat {

    void addChat(List<Long> usersId) throws SQLException;

    boolean isChatExist(List<Long> usersId) throws SQLException;

    List<Long> getChatsByUserId(Long userId) throws SQLException;

    Chat getChatById(Long chatId) throws SQLException;

    List<Long> getMessagesFromChat(Long chatId) throws SQLException;

    void addMessage(Long chatId, Message message) throws SQLException;

    Message getMessageById(Long messageId) throws SQLException;
}
