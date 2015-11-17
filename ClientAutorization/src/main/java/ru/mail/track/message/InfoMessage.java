package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

public class InfoMessage extends Message {
    private String info = null;

    public InfoMessage() { this.setType(CommandType.MSG_INFO);}

    public void setInfo(String info){
        this.info = info;
    }

    public String getInfo() {
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
