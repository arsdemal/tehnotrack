package ru.mail.track.commands;

import ru.mail.track.history.HistoryStore;
import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.io.IOException;

public class FindCommand implements Command {

    private HistoryStore historyStore;

    public FindCommand(HistoryStore historyStore) { this.historyStore = historyStore; }

    @Override
    public void execute(Session session, Message message) throws IOException {

        /*if (session.getSessionUser() == null) {
            System.out.println("You don't login");
        } else {
            if (args.length != 2) {
                System.out.println("Incorrect");
            } else {
                //historyStore.setFileUserId(session.getSessionUser().getId());
                historyStore.findHistory(args[1]);
            }
        }*/
    }
}
