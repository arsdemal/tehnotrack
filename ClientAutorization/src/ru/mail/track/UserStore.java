package ru.mail.track;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserStore {

    final static Charset ENCODING = StandardCharsets.UTF_8;
    final static String FILE_USER_STORE = "C:\\Users\\Arsdemal\\Documents\\Projects\\JAVA\\project2\\tehnotrack\\" +
            "ClientAutorization\\resources\\userstore.txt";

    ArrayList<User> users = new ArrayList<User>();

    void readFile() throws IOException {
        Path path = Paths.get(FILE_USER_STORE);
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                User user = new User(parts[0],parts[1]);
                addUser(user);
            }
        }
    }

    void writeFile() throws IOException {
        Path path = Paths.get(FILE_USER_STORE);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
            int userSize = users.size();
            for (int i = 0; i < userSize; i++){
                String line = new String(users.get(i).getName()+" "+users.get(i).getPass());
                writer.write(line);
                writer.newLine();
            }
        }
    }


    // Вам нужно выбрать, как вы будете хранить ваших пользователей, например в массиве User users[] = new User[100];

    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true
    boolean isUserExist(String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Добавить пользователя в хранилище
    void addUser(User user) {
        users.add(user);
    }

    // Получить пользователя по имени и паролю
    User getUser(String name, String pass) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name) && users.get(i).getPass().equals(pass)) {
                return users.get(i);
            }
        }
        return null;
    }
}
