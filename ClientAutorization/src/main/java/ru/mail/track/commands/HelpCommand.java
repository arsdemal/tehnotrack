package ru.mail.track.commands;

import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.Map;

public class HelpCommand implements Command {

    private Map<CommandType, Command> commands;

    public HelpCommand(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Session session, Message message) {
        InfoMessage infoMessage = new InfoMessage();
        infoMessage.setType(CommandType.MSG_INFO);
        infoMessage.setInfo("test");
        try {
            session.getConnectionHandler().send(infoMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}