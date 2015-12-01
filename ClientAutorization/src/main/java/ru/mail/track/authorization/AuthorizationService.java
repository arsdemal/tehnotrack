package ru.mail.track.authorization;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.User;
import ru.mail.track.message.UserStore;

public class AuthorizationService {

    static Logger log = LoggerFactory.getLogger(AuthorizationService.class);

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    public User createUser(LoginMessage loginMessage) {
        if (userStore.isUserExist(loginMessage.getLogin())) {
            log.info("A user with that username already exists");
            return null;
        } else {
            log.info("Create password");
            User user = new User(loginMessage.getLogin(), loginMessage.getPass());
            userStore.addUser(user);
            return user;
        }
    }
}