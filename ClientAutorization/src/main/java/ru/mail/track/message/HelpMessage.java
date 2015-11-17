package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

public class HelpMessage extends Message {


    public HelpMessage() {
        setType(CommandType.USER_HELP);
    }

    @Override
    public String toString() {
        return "HelpMessage";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        return false;
    }

}
