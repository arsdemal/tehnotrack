package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 * сообщение
 */
public class ChatListMessage extends Message {

    public ChatListMessage() {
        setType(CommandType.CHAT_LIST);
    }

}
