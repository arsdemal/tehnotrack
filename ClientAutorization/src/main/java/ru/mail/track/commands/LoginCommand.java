package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

import java.io.IOException;

/**
 *  логинимся если приходит полное сообщение
 */
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
        InfoMessage infoMsg = new InfoMessage();
        infoMsg.setType(CommandType.MSG_INFO);

        if (session.getSessionUser() != null) {

            log.info("User {} already logged in.", session.getSessionUser()); // выводим информацию на сервер
            infoMsg.setInfo("User already logged in."); // дублируем сообщение и отправляем пользователю

        } else {

            LoginMessage loginMsg = (LoginMessage) message;

            if (loginMsg.getLogin() != null && loginMsg.getPass() != null) {        // логинимся
                user = userStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                if (user == null) {
                    log.info("User {} no exist.", session.getSessionUser());
                    infoMsg.setInfo("User no exist.");
                }
            } else {
                log.info("Incorrect data");
                infoMsg.setInfo("Incorrect data");
            }

            if (user != null) {
                session.setSessionUser(user);
                sessionManager.registerUser(user.getId(), session.getId());
                log.info("Success login: {}", user);
                infoMsg.setInfo("Success login:");
            } else {
                log.info("No success login");
                infoMsg.setInfo("No success login");
            }
        }

        try {
            session.getConnectionHandler().send(infoMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}