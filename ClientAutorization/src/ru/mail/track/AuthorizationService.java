package ru.mail.track;

import java.io.IOException;
import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    Scanner scanner = new Scanner(System.in);

    /*void startAuthorization() {
        if (isLogin()) {
            login();
        }
    }*/

    User login(String name, String password) {
//            1. Ask for name
//            2. Ask for password
//            3. Ask UserStore for user:  userStore.getUser(name, passL)
        User user;
        if ( userStore.isUserExist(name) == false){
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

    User createUser() throws IOException {
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)
        System.out.println("Enter your user name");
        String name;
        while (true) {
            name = scanner.next();
            if (!userStore.isUserExist(name)) {
                break;
            }
            else {
                System.out.println("A user with that username already exists");
            }
        }
        System.out.println("Create password");
        String password = scanner.next();
        User user = new User(name,password);
        userStore.addUser(user);
        return user;
    }

    /*boolean isLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!");
        System.out.println("Do you want to register or login?");
        System.out.println("R/L?");
        String change = scanner.next();
        if (change.equals("R")) {
            createUser();
            return false;
        }

        return true;
    }*/
}
