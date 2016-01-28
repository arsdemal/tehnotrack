package ru.mail.track.commands;

import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserInfoMessage;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class UserInfoCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws IOException {
        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> info = new ArrayList<>();

        UserInfoMessage userMessage = (UserInfoMessage) message;

        if (session.getSessionUser() == null ) {
            info.add("You are not logged in");
        } else {
            //session.getSessionUser().setName(userMessage.getUserName());
            info.add("Success");
        }

        infoMessage.setInfo(info);
        session.getConnectionHandler().send(session, infoMessage);
    }
}
