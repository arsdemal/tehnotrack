package ru.mail.track.history;

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
import java.util.Calendar;

public class FileHistoryStore implements HistoryStore {

    final static Charset ENCODING = StandardCharsets.UTF_8;
    private String FILE_USER_HISTORY_STORE;

    private ArrayList<String> listHistory;

    public FileHistoryStore() {
        listHistory = new ArrayList<>();
    }

    @Override
    public void setFileUserId(Integer id) throws IOException {
        FILE_USER_HISTORY_STORE = "C:\\Users\\Arsdemal\\Documents\\Projects\\JAVA\\" +
            "project2\\tehnotrack\\ClientAutorization\\resources\\historystore\\" + id + ".txt";
        readFileUserId();
    }

    private void readFileUserId() throws IOException {

        listHistory.clear();
        File file = new File(FILE_USER_HISTORY_STORE);
        if (file.exists()) {
            Path path = Paths.get(FILE_USER_HISTORY_STORE);
            try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
                String line;
                while ((line = reader.readLine()) != null) {
                    listHistory.add(line);
                }
            } catch ( IOException e ) {
                e.printStackTrace();
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

    @Override
    public void addHistory(String str) throws IOException {
        listHistory.add(str + " " + Calendar.getInstance().getTime().toString());
        writeFileHistory();
    }

    @Override
    public void findHistory(String str) {
        int listHistorySize = listHistory.size();
        boolean flag = false;
        for (int i = 0; i < listHistorySize - 1; i++) {
            if (listHistory.get(i).contains(str)) {
                System.out.println(listHistory.get(i));
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Not found");
        }

    }

    @Override
    public void printHistory() {
        int listHistorySize = listHistory.size();
        for ( int i = 0; i < listHistorySize - 1; i++) {
            System.out.println(listHistory.get(i));
        }
    }

    @Override
    public void printHistory(int N) {
        System.out.println(N);
        int listHistorySize = listHistory.size();
        for (int i = (listHistorySize - N < 0) ? 0 : listHistorySize - N ; i < listHistorySize - 1; i++) {
            System.out.println(listHistory.get(i));
        }
    }
}
