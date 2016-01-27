package ru.mail.track.commands;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.io.IOException;


public interface  Command {

    void execute(Session session, Message message) throws IOException;
}