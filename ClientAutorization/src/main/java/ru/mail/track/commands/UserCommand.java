package ru.mail.track.commands;

import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserMessage;
import ru.mail.track.message.UserStore;
import ru.mail.track.session.Session;

import java.io.IOException;

public class UserCommand implements Command {

    private UserStore userStore;

    public UserCommand(UserStore userStore) { this.userStore = userStore; }

    @Override
    public void execute(Session session, Message message) throws IOException {

        InfoMessage info = new InfoMessage();
        info.setType(CommandType.MSG_INFO);
        UserMessage userMessage = (UserMessage) message;

        if (session.getSessionUser() == null ) {
            info.setInfo("You are not logged in");
        } else {
            session.getSessionUser().setName(userMessage.getUserName());
            info.setInfo("Success");
        }

        session.getConnectionHandler().send(info);
    }
}