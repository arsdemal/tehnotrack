package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ChatCreateMessage extends Message {

    List<Long> usersId = new ArrayList<Long>();

    public ChatCreateMessage() {
        setType(CommandType.CHAT_CREATE);
    }

    public void setUsersId(List<Long> usersId) {
        this.usersId = usersId;
    }

    public List<Long> getUsersId() {
        return usersId;
    }
}
