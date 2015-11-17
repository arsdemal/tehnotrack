package ru.mail.track.commands;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.io.IOException;

/**
 * отправка сообщения
 */
public class ChatSendCommand implements ChatCommand {
    @Override
    public void execute(Session session, Message message) throws IOException {

    }
}
