package ru.mail.track.commands;

import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserPassMessage;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class UserPassCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws IOException {

        UserPassMessage passMsg = (UserPassMessage) message;
        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> info = new ArrayList<>();

        if (session.getSessionUser() == null ) {
            info.add("You are not logged in");
        } else {
            if (!session.getSessionUser().getPass().equals(passMsg.getOldPass())) {
                info.add("Password mismatch");
            } else {
                session.getSessionUser().setPass(passMsg.getNewPass());
                info.add("Success");
            }

        }
        infoMessage.setInfo(info);
        session.getConnectionHandler().send(session, infoMessage);
        //return infoMessage;
    }
}
