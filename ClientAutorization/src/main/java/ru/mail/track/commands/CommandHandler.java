package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.net.MessageListener;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.Map;

/**
 * получает сообщение от connectionHandler
 * выполняет его
 *
 */
public class CommandHandler implements MessageListener {

    static Logger log = LoggerFactory.getLogger(CommandHandler.class);

    Map<CommandType, Command> commands;

    public CommandHandler(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void onMessage(Session session, Message message) {
        Command cmd = commands.get(message.getType());
        log.info("onMessage: {} type {}", message, message.getType());
        try {
            cmd.execute(session, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}