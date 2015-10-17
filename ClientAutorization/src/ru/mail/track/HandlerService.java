package ru.mail.track;

import java.io.IOException;
import java.util.Scanner;

public class HandlerService {

    private User mainUser = null;
    final static String FILE_USER_STORE = "C:\\Users\\Arsdemal\\Documents\\Projects\\JAVA\\tehnotrack\\ClientAutoriz" +
            "ation\\resources\\userstore.txt";

    public int startHandler() throws IOException {

        UserStore userStore = new UserStore();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            String str = scanner.nextLine();
            String[] parts = str.split(" ");

            if( mainUser != null) {
                mainUser.historyStore.addHistory(str);
            }

            if (parts[0].charAt(0) == '\\') {
                switch (parts[0]) {
                    case "\\help":
                        System.out.println("You can use commands: user, history, exit");
                        break;

                    case "\\user":
                        if( mainUser == null) {
                            System.out.println("You no login");
                        } else {
                            if (parts.length != 2) {
                                System.out.println("Incorrect command");
                            } else {
                                mainUser.setName(parts[1]);
                                userStore.writeFile(FILE_USER_STORE);
                            }

                        }
                        break;

                    case "\\history":
                        if (mainUser == null) {
                            System.out.println("Yon no login");
                        } else {
                            if( parts.length > 2) {
                                System.out.println("Incorrect");
                            } else {
                                if( parts.length == 2){
                                    mainUser.historyStore.printHistory(Integer.parseInt(parts[1]));
                                } else {
                                    mainUser.historyStore.printHistory();
                                }
                            }
                        }
                        break;

                    case "\\login":
                        userStore.readFile(FILE_USER_STORE);
                        AuthorizationService service = new AuthorizationService(userStore);

                        if (parts.length == 3) {
                            mainUser = service.login(parts[1],parts[2]);
                            if (mainUser != null) {
                                System.out.println("Welcome");
                            }
                        } else {
                            if (parts.length == 1) {
                                mainUser = service.createUser();
                                if( mainUser != null) {
                                    userStore.writeFile(FILE_USER_STORE);
                                    System.out.println("Welcome");
                                }
                            }
                            else {
                                System.out.println("Incorrect");
                            }
                        }
                        break;

                    default:
                        System.out.println();
                        break;
                }
                if (parts[0].equals("\\exit")) {
                    break;
                }
            }

        }

        return 0;
    }
}