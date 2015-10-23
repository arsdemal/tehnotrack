package ru.mail.track;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ru.mail.track.commands.Command;
import ru.mail.track.history.HistoryStore;
import ru.mail.track.session.Session;


public class InputHandler {

    private Session session;

    private Map<String, Command> commandMap;

    private HistoryStore historyStore;

    public InputHandler(Session session, Map<String, Command> commandMap, HistoryStore historyStore) {
        this.session = session;
        this.commandMap = commandMap;
        this.historyStore = historyStore;
    }

    public void handle(String data) throws IOException {

        if(session.getSessionUser() != null) {
            historyStore.setFileNameUser(session.getSessionUser().getName());
            historyStore.addHistory(data);
        }

        if (data.startsWith("\\")) {

            String[] tokens = data.split(" ");
            if (commandMap.containsKey(tokens[0])) {
                Command cmd = commandMap.get(tokens[0]);
                cmd.execute(session, tokens);
            }

        } else {
            System.out.println(">" + data);
        }
    }

}