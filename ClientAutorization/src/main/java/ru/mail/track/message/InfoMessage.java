package ru.mail.track.message;
/**
 *  Информирующее сообщение от клиента
 */

import ru.mail.track.commands.CommandType;

import java.util.List;

public class InfoMessage extends Message {
    private List<String> info = null;

    public InfoMessage() { this.setType(CommandType.MSG_INFO);}

    public void setInfo(List<String> info){
        this.info = info;
    }

    public List<String> getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "InfoMessage";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        return false;
    }
}
