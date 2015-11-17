package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 *
 */
public class UserInfoMessage extends Message {

    //private String userInfo;

    public UserInfoMessage() {
        setType(CommandType.USER_INF0);
    }



    //public void SetUserInfo(String userName) {this.userInfo = userName;}

    //public String getUserInfo() {return userInfo;}

    @Override
    public String toString() {
        return "UserInfoMessage";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        return false;
    }
}
