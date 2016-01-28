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
 *
 */
public class RegisterCommand implements Command {

    static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private SessionManager sessionManager;
    private UserStore userStore;

    public RegisterCommand (UserStore userStore,SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, Message message) throws IOException {

        RegisterMessage regMsg = (RegisterMessage) message;
        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        List<String> info = new ArrayList<>();

        if (userStore.isUserExist(regMsg.getLogin())) {
            log.info("This user already exist");
            info.add("This user already exist");
        } else {
            User user = new User(regMsg.getLogin(),regMsg.getPass());
            userStore.addUser(user);
            session.setSessionUser(user);
            sessionManager.registerUser(user.getId(), session.getId());
            log.info("Success register");
            info.add("Success register");
        }
        infoMessage.setInfo(info);
        session.getConnectionHandler().send(session, infoMessage);
    }
}