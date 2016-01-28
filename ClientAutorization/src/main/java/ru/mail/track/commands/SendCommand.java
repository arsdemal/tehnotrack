package ru.mail.track.commands;

import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SendCommand implements Command {

    private MessageStore messageStore;
    private SessionManager sessionManager;

    public SendCommand(SessionManager sessionManager, MessageStore messageStore) {
        this.sessionManager = sessionManager;
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) {

        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> info = new ArrayList<>();

        if (session.getSessionUser() == null) {
            info.add("You are not logged in");
        } else {
            SendMessage sendMessage = (SendMessage) message;
            Chat chat = messageStore.getChatById(sendMessage.getChatId());
            // добавили сообщение в хранилище
            messageStore.addMessage(chat.getId(), message);
            List<Long> parts = chat.getParticipantIds();
            try {
                for (Long userId : parts) {
                    Session userSession = sessionManager.getSessionByUser(userId);
                    if (userSession != null) {
                        session.getConnectionHandler().send(userSession, message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            info.add("Message sent");
        }

        infoMessage.setInfo(info);
        try {
            session.getConnectionHandler().send(session, infoMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}