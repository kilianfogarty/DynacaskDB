package org.example;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class Bitcask {
    private final LogFile logFile;
    private final KeyDir keyDir;

    public Bitcask(String path) throws IOException {
        this.keyDir = new KeyDir();
        this.logFile = new LogFile(path);
        logFile.loadKeyDir(keyDir);
    }

    public void put(String key, String value) throws IOException {
        byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
        Record record = new Record(key, value);
        long recordStart = logFile.append(record);
        keyDir.put(key, recordStart, valueBytes.length);


        /*

        long recordStart = file.getFilePointer();

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);

        file.writeInt(keyBytes.length);
        file.writeInt(valueBytes.length);
        file.write(keyBytes);
        file.write(valueBytes);

        keyDir.put(key, new LogPointer(recordStart, valueBytes.length));

         */
    }

    public String get(String key) throws IOException {
        LogPointer ptr = keyDir.get(key);
        if (ptr == null) {
            return null;
        }
        Record record = logFile.read(ptr.recordStart);
        return record.value;

        /*

        file.seek(ptr.recordStart);
        int keyLength = file.readInt();
        int valueLength = file.readInt();

        file.skipBytes(keyLength);
        byte[] valueBytes = new byte[valueLength];
        file.readFully(valueBytes);

        return new String(valueBytes, StandardCharsets.UTF_8);

         */
    }

    /*
    private void loadKeyDir() throws IOException {
        file.seek(0);

        while (file.getFilePointer() < file.length()) {
            long recordStart = file.getFilePointer();

            int keyLength = file.readInt();
            int valueLength = file.readInt();

            byte[] keyBytes = new byte[keyLength];
            file.readFully(keyBytes);
            file.skipBytes(valueLength);

            String key = new String(keyBytes, StandardCharsets.UTF_8);
            keyDir.put(key, new LogPointer(recordStart, valueLength));
        }
    }
    */
}
