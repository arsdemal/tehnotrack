package ru.mail.track.commands;

import ru.mail.track.history.HistoryStore;
import ru.mail.track.session.Session;

import java.io.IOException;


public class HistoryCommand implements Command {

    private HistoryStore historyStore;

    public HistoryCommand(HistoryStore historyStore) { this.historyStore = historyStore; }

    @Override
    public void execute(Session session, String[] args) throws IOException {
        if ( session.getSessionUser() == null) {
            System.out.println("You don't login");
        } else {
            if ( args.length > 2) {
                System.out.println("Incorrect");
            } else {
                //historyStore.setFileUserId(session.getSessionUser().getId());
                if( args.length == 2) {
                    historyStore.printHistory(Integer.parseInt(args[1]));
                } else {
                    historyStore.printHistory();
                }
            }
        }
    }
}
