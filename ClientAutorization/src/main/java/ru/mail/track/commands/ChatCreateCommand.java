package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.ChatCreateMessage;
import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * создаем чат
 */
public class ChatCreateCommand implements Command{

    static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private MessageStore messageStore;

    public ChatCreateCommand(MessageStore messageStore){
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) throws IOException {

        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> info = new ArrayList<>();

        ChatCreateMessage chatCreateMessage = (ChatCreateMessage) message;
        List<Long> usersId = chatCreateMessage.getUsersId();
        if ( !usersId.contains(session.getSessionUser().getId())) { // если в списке юзеров нет самого юзера, то добавляем его
            usersId.add(session.getSessionUser().getId());
        }

        if ( messageStore.isChatExist(usersId)) {
            log.info("chat is exist");
            info.add("chat is exist");
        } else {
            messageStore.createChat(usersId);
            log.info("Success create chat");
            info.add("Success create chat");
        }

        infoMessage.setInfo(info);
        //return infoMessage;
        session.getConnectionHandler().send(infoMessage);
    }
}
