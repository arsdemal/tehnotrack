package ru.mail.track.commands;

import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserInfoMessage;
import ru.mail.track.session.Session;

import java.io.IOException;

/**
 *
 */
public class UserInfoCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws IOException {
        InfoMessage info = new InfoMessage();
        info.setType(CommandType.MSG_INFO);

        UserInfoMessage userMessage = (UserInfoMessage) message;

        if (session.getSessionUser() == null ) {
            info.setInfo("You are not logged in");
        } else {
            //session.getSessionUser().setName(userMessage.getUserName());
            info.setInfo("Success");
        }

        session.getConnectionHandler().send(info);

    }
}
