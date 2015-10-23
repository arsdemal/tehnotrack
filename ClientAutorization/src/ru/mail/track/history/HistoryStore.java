package ru.mail.track.history;


import java.io.IOException;

public interface HistoryStore {

    void setFileNameUser(String name) throws IOException;

    void addHistory(String str) throws IOException;

    void findHistory(String str);

    void printHistory();

    void printHistory(int N);
}
