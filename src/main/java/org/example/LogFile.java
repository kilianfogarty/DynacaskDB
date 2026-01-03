package org.example;

import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class LogFile {
    private final RandomAccessFile file;

    public LogFile(String path) throws IOException {
        this.file = new RandomAccessFile(path, "rw");
    }

    public long append(Record record) throws IOException {
        long recordStart = file.length();
        file.seek(recordStart);
        record.writeTo(file);
        return recordStart;
    }

    public Record read(long recordStart) throws IOException {
        file.seek(recordStart);
        return Record.readFrom(file);
    }

    public void loadKeyDir(KeyDir keyDir) throws IOException {
        file.seek(0);

        while(file.getFilePointer() < file.length()) {
            long recordStart = file.getFilePointer();
            Record record = Record.readFrom(file);
            keyDir.put(record.key, recordStart, record.valueBytes.length);
        }
    }


}
