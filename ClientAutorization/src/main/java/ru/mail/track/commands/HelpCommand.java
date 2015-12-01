package ru.mail.track.commands;

import ru.mail.track.message.InfoMessage;
import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        List<String> info = new ArrayList<>();
        info.add("");
        try {
            infoMessage.setInfo(info);
            session.getConnectionHandler().send(infoMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}