package ru.mail.track.commands;

import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.session.Session;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {

    private AuthorizationService service;

    public LoginCommand(AuthorizationService service) {
        this.service = service;
    }

    @Override
    public void execute(Session session, String[] args) {
        System.out.println("Executing login");
        if (session.getSessionUser() != null) {
                System.out.println("Вы уже вошли");
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

        }
    }
}