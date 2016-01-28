package ru.mail.track.jdbc;

import ru.mail.track.message.Chat;
import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;

import java.util.List;

/**
 *
 */
public interface DAOMessageStore extends MessageStore {

    void addChat(List<Long> usersId);

    boolean isChatExist(List<Long> usersId);

    List<Long> getChatsByUserId(Long userId);

    Chat getChatById(Long chatId);

    List<Long> getMessagesFromChat(Long chatId);

    void addMessage(Long chatId, Message message);

    Message getMessageById(Long messageId);
}
