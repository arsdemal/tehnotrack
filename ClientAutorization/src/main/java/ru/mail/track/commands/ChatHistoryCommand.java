package ru.mail.track.commands;

import ru.mail.track.message.*;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * история сообщений
 */
public class ChatHistoryCommand implements ChatCommand {

    private MessageStore messageStore;

    public ChatHistoryCommand(MessageStore messageStore) { this.messageStore = messageStore; }

    @Override
    public void execute(Session session, Message message) throws IOException {

        ChatHistoryMessage historyMsg = (ChatHistoryMessage) message;
        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);

        List<String> history = new ArrayList<>();
        List<String> info = new ArrayList<>();

        if (session.getSessionUser() != null) {
            for (Long messageId : messageStore.getMessagesFromChat(historyMsg.getChatId())) {
                SendMessage sendMessage = (SendMessage) messageStore.getMessageById(messageId);
                history.add(sendMessage.getMessage());
            }
        }   else {
            info.add("You no login");
            infoMessage.setInfo(info);
        }

        infoMessage.setInfo(history);
        session.getConnectionHandler().send(session, infoMessage);
        //return infoMessage;

    }
}
