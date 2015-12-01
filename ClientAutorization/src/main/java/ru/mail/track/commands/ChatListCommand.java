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

        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> info = new ArrayList<>();

        if (session.getSessionUser() == null) {
            info.add("No login");
        } else {
            String chatsId = "";
            // записываем информацию в строку
            List<Long> chatsList = (ArrayList)messageStore.getChatsByUserId(session.getSessionUser().getId());
            for ( Long chatLong : chatsList) {
                chatsId += chatLong.toString();
            }
            log.info(chatsId);
            info.add(chatsId);
        }

        infoMessage.setInfo(info);
        session.getConnectionHandler().send(infoMessage);
    }
}
