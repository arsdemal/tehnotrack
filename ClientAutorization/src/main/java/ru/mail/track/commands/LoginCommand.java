package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.User;
import ru.mail.track.message.UserStore;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

public class LoginCommand implements Command {

    static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private SessionManager sessionManager;
    private UserStore userStore;

    public LoginCommand(UserStore userStore,SessionManager sessionManager ) {
        this.sessionManager = sessionManager;
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, Message message) {

        User user = null;

        if (session.getSessionUser() != null) {
            log.info("User {} already logged in.", session.getSessionUser());
            return;
        } else {
            LoginMessage loginMsg = (LoginMessage) message;


            if (loginMsg.getLogin() != null && loginMsg.getPass() != null) {        // логинимся
                user = userStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                if (user == null) {
                    log.info("User {} no exist.", session.getSessionUser());
                    return;
                }
            } else {
                if (loginMsg.getLogin() == null && loginMsg.getPass() == null) {  // регистрируемся
                    AuthorizationService authorizationService = new AuthorizationService(userStore);
                    authorizationService.createUser(loginMsg);
                } else {
                    log.info("Incorrect data");
                }
            }

            session.setSessionUser(user);
            sessionManager.registerUser(user.getId(), session.getId());
            log.info("Success login: {}", user);
        }

    }
}