package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ChateCreateMessage extends Message {

    List<Long> usersId = new ArrayList<Long>();

    public ChateCreateMessage() {
        setType(CommandType.CHAT_CREATE);
    }

    public void chateAddUSer(Long id) {
        usersId.add(id);
    }
}
