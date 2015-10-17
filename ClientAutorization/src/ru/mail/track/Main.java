package ru.mail.track;

import java.io.IOException;

// example
// 2 example
public class Main {

    public static void main(String[] args) throws IOException {

        //UserStore userStore = new UserStore();
        HandlerService service = new HandlerService();
        //AuthorizationService service = new AuthorizationService(userStore);

        //service.startAuthorization();
        service.startHandler();
    }
}
