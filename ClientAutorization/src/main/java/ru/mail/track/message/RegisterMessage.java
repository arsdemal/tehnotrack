package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 *
 */
public class RegisterMessage extends Message {

    private String login;

    private String pass;

    public RegisterMessage() { setType(CommandType.USER_REG); }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPass() { return pass; }

    public void setPass(String pass) { this.pass = pass; }
}
