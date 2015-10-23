package ru.mail.track.authorization;


import ru.mail.track.session.User;

import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    public User login(String name, String password) {

        User user;
        if (!userStore.isUserExist(name)){
            System.out.println("A user with this name no exist");
            return null;
        } else {
            if ( (user = userStore.getUser(name,password)) == null) {
                System.out.println("Incorrect password");
                return null;
            } else {
                return user;
            }
        }
    }

    public User createUser() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your user name");
        String name = scanner.next();
        if (userStore.isUserExist(name)) {
            System.out.println("A user with that username already exists");
            return null;
        } else {
            System.out.println("Create password");
            //String password = scanner.next();
            User user = new User(name,scanner.next());
            userStore.addUser(user);
            return user;
        }


    }
}