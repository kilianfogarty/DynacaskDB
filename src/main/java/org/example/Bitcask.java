package org.example;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class Bitcask {
    final RandomAccessFile file;
    private final Map<String, LogPointer> keyDir = new HashMap<>();

    public Bitcask(String path) throws IOException {
        this.file = new RandomAccessFile(path, "rw");
        file.seek(file.length());
    }

    public void put(String key, String value) throws IOException {
        long offset = file.getFilePointer();

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);

        file.writeInt(keyBytes.length);
        file.writeInt(valueBytes.length);
        file.write(keyBytes);
        file.write(valueBytes);

        keyDir.put(key, new LogPointer(offset, valueBytes.length));
    }

    public String get(String key) throws IOException {
        LogPointer ptr = keyDir.get(key);
        if (ptr == null) {
            return null;
        }
        file.seek(ptr.offset);
        int keyLength = file.readInt();
        int valueLength = file.readInt();

        file.skipBytes(keyLength);
        byte[] valueBytes = new byte[valueLength];
        file.readFully(valueBytes);

        return new String(valueBytes, StandardCharsets.UTF_8);
    }
}
