package ru.mail.track.commands;

import ru.mail.track.message.*;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * поиск по сообщениям
 */
public class ChatFindCommand implements ChatCommand {

    private MessageStore messageStore;

    public ChatFindCommand(MessageStore messageStore) { this.messageStore = messageStore;}

    @Override
    public void execute(Session session, Message message) throws IOException {

        ChatFindMessage findMessage = (ChatFindMessage) message;

        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);

        List<String> info = new ArrayList<>();

        if (session.getSessionUser() != null) {
            for (Long messageId : messageStore.getMessagesFromChat(findMessage.getChatId())) {
                SendMessage sendMessage = (SendMessage) messageStore.getMessageById(messageId);
                if (sendMessage.getMessage().contains(findMessage.getRegex()))
                    info.add(sendMessage.getMessage());
            }
        }   else {
            info.add("You no login");
            infoMessage.setInfo(info);
        }

        infoMessage.setInfo(info);
        session.getConnectionHandler().send(infoMessage);
        //return infoMessage;

    }
}
