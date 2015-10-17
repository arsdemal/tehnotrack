package ru.mail.track;


import java.io.IOException;

public class User {
    private String name;
    private String pass;
    public HistoryStore historyStore;

    public User(String name, String pass) throws IOException {
        this.name = name;
        this.pass = pass;
        historyStore = new HistoryStore(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
