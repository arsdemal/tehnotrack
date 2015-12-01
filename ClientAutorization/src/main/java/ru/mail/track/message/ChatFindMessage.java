package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 *
 */
public class ChatFindMessage extends Message {

    private Long chatId;
    private String regex;

    public ChatFindMessage() {setType(CommandType.CHAT_FIND);}

    public void setChatId( Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setRegex( String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
