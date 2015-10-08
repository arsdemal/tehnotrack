package ru.mail.track;

// example
// 2 example
public class Main {

    public static void main(String[] args) {

        UserStore userStore = new UserStore();
        AuthorizationService service = new AuthorizationService(userStore);

        service.startAuthorization();

    }
}
