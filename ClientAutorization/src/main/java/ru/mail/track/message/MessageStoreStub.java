package ru.mail.track.message;

import ru.mail.track.jdbc.DAOChat;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class MessageStoreStub implements MessageStore {

    public static final AtomicLong counter = new AtomicLong(0);

    private DAOChat chats;

    List<SendMessage> messages1 = Arrays.asList(
            new SendMessage(1L, "msg1_1"),
            new SendMessage(1L, "msg1_2"),
            new SendMessage(1L, "msg1_3"),
            new SendMessage(1L, "msg1_4"),
            new SendMessage(1L, "msg1_5")
    );
    List<SendMessage> messages2 = Arrays.asList(
            new SendMessage(2L, "msg2_1"),
            new SendMessage(2L, "msg2_2"),
            new SendMessage(2L, "msg2_3"),
            new SendMessage(2L, "msg2_4"),
            new SendMessage(2L, "msg2_5")
    );

    Map<Long, Message> messages = new HashMap<>();

    public MessageStoreStub(DAOChat daoChat){
        this.chats = daoChat;
    }

    /*
    static Map<Long, Chat> chats = new HashMap<>();

    static {
        Chat chat1 = new Chat();
        chat1.setId(counter.incrementAndGet());
        chat1.addParticipant(0L);
        chat1.addParticipant(2L);

        Chat chat2 = new Chat();
        chat2.setId(counter.incrementAndGet());
        chat2.addParticipant(1L);
        chat2.addParticipant(2L);
        chat2.addParticipant(3L);

        chats.put(1L, chat1);
        chats.put(2L, chat2);
    }*/

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