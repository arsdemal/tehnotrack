package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 *
 */
public class ChatHistoryMessage extends Message {

    private Long chatId;

    public ChatHistoryMessage() {setType(CommandType.CHAT_HISTORY);}

    public void setChatId(Long chatId){
       this.chatId = chatId;
    }

    public Long getChatId(){
        return chatId;
    }
}
