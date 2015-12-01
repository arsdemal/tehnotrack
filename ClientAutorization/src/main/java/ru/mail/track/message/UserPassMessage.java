package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 *
 */
public class UserPassMessage extends Message{

    private String userNewPass;
    private String userOldPass;

    public UserPassMessage() {
        setType(CommandType.USER_PASS);
    }

    public void setNewPass(String userNewPass) {this.userNewPass = userNewPass;}

    public String getNewPass() {return userNewPass;}

    public void setOldPass(String userOldPass) {this.userOldPass = userOldPass;}

    public String getOldPass() {return userOldPass;}

    @Override
    public String toString() {
        return "UserPassMessage";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPassMessage that = (UserPassMessage) o;

        if (userOldPass != null ? !userOldPass.equals(that.userOldPass) : that.userOldPass != null) return false;
        return !(userNewPass != null ? !userNewPass.equals(that.userNewPass) : that.userNewPass != null);

    }
}
