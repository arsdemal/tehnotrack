package ru.mail.track.authorization;

import ru.mail.track.session.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 */
public class FileUserStore implements UserStore {

    final static Charset ENCODING = StandardCharsets.UTF_8;
    final static String FILE_USER_STORE = "C:\\Users\\Arsdemal\\Documents\\Projects\\JAVA\\project2\\tehnotrack\\" +
            "ClientAutorization\\resources\\userstore.txt";
    public FileUserStore() {
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList<User> users = new ArrayList<>();

    private void readFile() throws IOException {
        Path path = Paths.get(FILE_USER_STORE);
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                User user = new User(parts[0],parts[1]);
                addUser(user);
            }
        }
    }

    private void writeFile() throws IOException {
        Path path = Paths.get(FILE_USER_STORE);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
            int userSize = users.size();
            for (int i = 0; i < userSize; i++){
                writer.write(users.get(i).getName()+" "+users.get(i).getPass());
                writer.newLine();
            }
        }
    }

    @Override
    public boolean isUserExist(String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String name, String pass) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name) && users.get(i).getPass().equals(pass)) {
                return users.get(i);
            }
        }
        return null;
    }
}