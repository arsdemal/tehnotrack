package ru.mail.track.commands;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.util.Map;

/**
 * ������� ������
 */
public class HelpCommand implements Command {

    private Map<CommandType, Command> commands;

    public HelpCommand(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Session session, Message message) {
        System.out.println("Executing help");
    }
}