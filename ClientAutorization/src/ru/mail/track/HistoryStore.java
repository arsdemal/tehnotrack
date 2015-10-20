package ru.mail.track;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arsdemal on 17.10.2015.
 */
public class HistoryStore {

    private ArrayList<String> listHistory;
    final static Charset ENCODING = StandardCharsets.UTF_8;
    final String FILE_USER_HISTORY_STORE;

    public HistoryStore(String name) throws IOException {
        FILE_USER_HISTORY_STORE = "C:\\Users\\Arsdemal\\Documents\\Projects\\JAVA\\project2\\tehnotrack\\" +
                "ClientAutorization\\resources\\historystore\\" + name + ".txt";
        listHistory = new ArrayList<String>();
        File file = new File(FILE_USER_HISTORY_STORE);
        if (file.exists()) {
            readFileHistory();
        }
    }

    private void readFileHistory() throws IOException {
        Path path = Paths.get(FILE_USER_HISTORY_STORE);
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
            String line = null;
            while ((line = reader.readLine()) != null) {
                listHistory.add(line);
            }
        }
    }

    private void writeFileHistory() throws IOException {
        Path path = Paths.get(FILE_USER_HISTORY_STORE);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
            for(String line : listHistory){
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public void addHistory(String line) throws IOException {
        listHistory.add(line);
        writeFileHistory();
    }

    public void findHistory(String str) {
        int listHistorySize = listHistory.size();
        boolean flag = false;
        for (int i = 0; i < listHistorySize - 1; i++) {
            if (listHistory.get(i).indexOf(str) != -1) {
                System.out.println(listHistory.get(i));
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Not found");
        }
    }

    public void printHistory () {
        int listHistorySize = listHistory.size();
        for ( int i = 0; i < listHistorySize - 1; i++) {
            System.out.println(listHistory.get(i));
        }
    }

    public void printHistory (int N) {
        System.out.println(N);
        int listHistorySize = listHistory.size();
        int i;
        if( listHistorySize - N < 0) {
            i = 0;
        } else {
            i = listHistorySize - N;
        }
        for ( ; i < listHistorySize - 1; i++) {
            System.out.println(listHistory.get(i));
        }
    }

}
