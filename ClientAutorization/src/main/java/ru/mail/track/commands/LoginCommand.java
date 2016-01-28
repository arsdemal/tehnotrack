package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> info = new ArrayList<>();

        if (session.getSessionUser() != null) {
            // выводим информацию на сервер
            log.info("User {} already logged in.", session.getSessionUser());
            // дублируем сообщение и отправляем пользователю
            info.add("User already logged in.");
        } else {
            LoginMessage loginMsg = (LoginMessage) message;
            // логинимся
            if (loginMsg.getLogin() != null && loginMsg.getPass() != null) {
                user = userStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                if (user == null) {
                    log.info("User {} no exist.", session.getSessionUser());
                    info.add("User no exist.");
                }
            } else {
                log.info("Incorrect data");
                info.add("Incorrect data");
            }
            if (user != null) {
                session.setSessionUser(user);
                sessionManager.registerUser(user.getId(), session.getId());
                log.info("Success login: {}", user);
                info.add("Success login:");
            } else {
                log.info("No success login");
                info.add("No success login");
            }
        }

        infoMessage.setInfo(info);
        try {
            session.getConnectionHandler().send(session, infoMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}