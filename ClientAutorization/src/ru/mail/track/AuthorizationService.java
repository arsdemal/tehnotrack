package ru.mail.track;

import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    Scanner scanner = new Scanner(System.in);

    void startAuthorization() {
        User userExample[] = new User[4];
        userExample[0] = new User("Misha","123");
        userExample[1] = new User("Lesha","2345");
        userExample[2] = new User("Tania","password");
        userExample[3] = new User("Arseniy","qqqq");
        for( int i = 0; i < 4; i++){
            userStore.addUser(userExample[i]);
        }
        if (isLogin()) {
            login();
        }
    }

    User login() {
//            1. Ask for name
//            2. Ask for password
//            3. Ask UserStore for user:  userStore.getUser(name, passL)

        System.out.println("Name user:");
        String name = scanner.next();
        while(true){
            if(!userStore.isUserExist(name)){
                System.out.println("This user doesn't exist");
            }
            else
                break;
            name = scanner.next();
        }

        System.out.println("Password:");
        while(true) {
            String password = scanner.next();
            if (userStore.getUser(name, password) != null) {
                System.out.println("Hello ");
                System.out.println(name);
                break;
            } else {
                System.out.println("Incorrect password");
            }
        }


        return null;
    }

    User createUser() {
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)
        System.out.println("Enter your user name");
        String name = scanner.next();
        while( true ){
            if(userStore.isUserExist(name)){
                System.out.println("A user with that username already exists");
            }
            else
                break;
            name = scanner.next();
        }
        System.out.println("Create password");
        String password = scanner.next();
        User user = new User(name,password);
        userStore.addUser(user);
        return null;
    }

    boolean isLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!");
        System.out.println("Do you want to register or login?");
        System.out.println("R/L?");
        String change = scanner.next();
        String str = "R";
        if(change.equals(str)){
            createUser();
            return false;
        }

        return true;
    }
}
