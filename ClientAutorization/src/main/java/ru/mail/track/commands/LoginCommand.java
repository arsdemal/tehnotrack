package ru.mail.track.commands;

import ru.mail.track.message.UserStore;
import ru.mail.track.message.Message;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

public class LoginCommand implements Command {

    //private AuthorizationService service;
    private SessionManager sessionManager;
    private UserStore userStore;

    public LoginCommand(UserStore userStore,SessionManager sessionManager ) {
        this.sessionManager = sessionManager;
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, Message message) {


        /*System.out.println("Executing login");
        if (session.getSessionUser() != null) {
                System.out.println("�� ��� �����");
        } else {
            if (args.length == 3) {
                session.setSessionUser(service.login(args[1], args[2]));
            } else {
                if (args.length == 1) {
                    session.setSessionUser(service.createUser());
                } else {
                    System.out.println("Incorrect");
                }
            }

        }*/
    }
}