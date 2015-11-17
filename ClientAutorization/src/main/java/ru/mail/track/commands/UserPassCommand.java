package ru.mail.track.commands;

import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserPassMessage;
import ru.mail.track.session.Session;

import java.io.IOException;

/**
 *
 */

public class UserPassCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws IOException {

        UserPassMessage passMsg = (UserPassMessage) message;
        InfoMessage info = new InfoMessage();
        info.setType(CommandType.MSG_INFO);

        if (session.getSessionUser() == null ) {
            info.setInfo("You are not logged in");
        } else {
            if (!session.getSessionUser().getPass().equals(passMsg.getOldPass())) {
                info.setInfo("Password mismatch");
            } else {
                session.getSessionUser().setPass(passMsg.getNewPass());
                info.setInfo("Success");
            }

        }
        session.getConnectionHandler().send(info);
    }
}
