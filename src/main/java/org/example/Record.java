package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Record {
    public final String key;
    public final byte[] keyBytes;
    public final String value;
    public final byte[] valueBytes;

    public Record(String key, String value) {
        this.key = key;
        this.keyBytes = key.getBytes(StandardCharsets.UTF_8);
        this.value = value;
        this.valueBytes = value.getBytes(StandardCharsets.UTF_8);
    }

    public int size() {
        int keySize = keyBytes.length;
        int valueSize = valueBytes.length;

        return Integer.BYTES * 2 + keySize + valueSize;
    }

    public void writeTo(RandomAccessFile file) throws IOException {
        file.writeInt(keyBytes.length);
        file.writeInt(valueBytes.length);
        file.write(keyBytes);
        file.write(valueBytes);
    }

    public static Record readFrom(RandomAccessFile file) throws IOException {
        int keyLength = file.readInt();
        int valueLength = file.readInt();

        byte[] keyBytes = new byte[keyLength];
        file.readFully(keyBytes);

        byte[] valueBytes = new byte[valueLength];
        file.readFully(valueBytes);

        return new Record(
                new String(keyBytes, StandardCharsets.UTF_8),
                new String(valueBytes, StandardCharsets.UTF_8)
        );
    }
}
