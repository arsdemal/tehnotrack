package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 *
 */
public class UserMessage extends Message {

    private String userName;

    public UserMessage() {setType(CommandType.USER_NICK);}

    public void setUserName(String userName) {this.userName = userName;}

    public String getUserName() {return userName;}

    @Override
    public String toString() {return "UserMessage";}

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        return false;
    }

}
