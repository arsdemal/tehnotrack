package ru.mail.track.commands;

import ru.mail.track.message.Message;
import ru.mail.track.message.UserStore;
import ru.mail.track.session.Session;

import java.io.IOException;

public class UserCommand implements Command {

    private UserStore userStore;

    public UserCommand(UserStore userStore) { this.userStore = userStore; }

    @Override
    public void execute(Session session, Message message) throws IOException {


        /*if (session.getSessionUser() == null ) {
            System.out.println("You don't login");
        } else {
            if (args.length != 2) {
                System.out.println("Incorrect");
            } else {
                userStore.ChangeNameUser(session.getSessionUser(),args[1]);
            }
        }*/
    }
}
