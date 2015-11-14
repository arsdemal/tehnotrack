package ru.mail.track.session;


public class User {
    private String name;
    private String pass;
    private Integer id;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() { return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }
}