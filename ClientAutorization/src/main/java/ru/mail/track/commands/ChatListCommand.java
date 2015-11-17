package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Выводим список чатов
 */
public class ChatListCommand implements ChatCommand {

    private MessageStore messageStore;
    static Logger log = LoggerFactory.getLogger(ChatListCommand.class);

    public ChatListCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) throws IOException {
        if (session.getSessionUser() == null) {
            InfoMessage info = new InfoMessage();
            info.setType(CommandType.MSG_INFO);
            info.setInfo("No login");
            session.getConnectionHandler().send(info);
        } else {
            InfoMessage infoMessage = new InfoMessage();
            infoMessage.setType(CommandType.MSG_INFO);
            String chatsId = "";
            // записываем информацию в строку
            List<Long> chatsList = (ArrayList)messageStore.getChatsByUserId(session.getSessionUser().getId());
            for ( Long chatLong : chatsList) {
                chatsId += chatLong.toString();
            }
            log.info(chatsId);
            infoMessage.setInfo(chatsId);
            session.getConnectionHandler().send(infoMessage);
        }
    }
}
