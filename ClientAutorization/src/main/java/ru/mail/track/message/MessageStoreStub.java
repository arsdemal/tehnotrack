package ru.mail.track.message;

import ru.mail.track.jdbc.DAOMessageStore;

import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class MessageStoreStub implements MessageStore {

    private DAOMessageStore chats;

    public MessageStoreStub(DAOMessageStore daoMessageStore){
        this.chats = daoMessageStore;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) {
        try {
            return chats.getChatsByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Chat getChatById(Long chatId) {
        try {
            return chats.getChatById(chatId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        try {
            return chats.getMessagesFromChat(chatId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message getMessageById(Long messageId) {
        try {
            return chats.getMessageById(messageId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addMessage(Long chatId, Message message) {
        try {
            chats.addMessage(chatId,message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {

    }

    @Override
    public void createChat(List<Long> usersId){
        try {
            chats.addChat(usersId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isChatExist(List<Long> usersId) {
        try {
            return chats.isChatExist(usersId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}