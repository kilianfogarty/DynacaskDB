package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Record {
    public final String key;
    public final byte[] keyBytes;
    public final String value; //Should probably get rid of this for simplification
    public final byte[] valueBytes;

    public static final int tombstone = -1;

    public Record(String key, String value) {
        this.key = key;
        this.keyBytes = key.getBytes(StandardCharsets.UTF_8);
        this.value = value;

        if (value == null) {
            this.valueBytes = null;
        } else {
            this.valueBytes = value.getBytes(StandardCharsets.UTF_8);
        }
    }

    // Extraneous and should probably be deleted unless I use it later
    public int size() {
        int keySize = keyBytes.length;
        int valueSize = valueBytes.length;

        // The int storing Key size + int storing Value size + space key takes up + space value takes up
        // KeyDir(name, Kilian) = 4 + 4 + 4 + 6 = 11
        return Integer.BYTES * 2 + keySize + valueSize;
    }

    // Helper for creating values to be deleted
    // Returns true if the record is a tombstone
    public boolean isTombstone() {
        return value == null;
    }

    public void writeTo(RandomAccessFile file) throws IOException {

        // Current record write is: Key size, value size, key, value
        file.writeInt(keyBytes.length);
        if (isTombstone()) {
            file.writeInt(tombstone);
        } else {
            file.writeInt(valueBytes.length);
        }
        file.write(keyBytes);
        if(!isTombstone()) {
            file.write(valueBytes);
        }
    }

    public static Record readFrom(RandomAccessFile file) throws IOException {
        int keyLength = file.readInt();
        int valueLength = file.readInt();

        byte[] keyBytes = new byte[keyLength];
        file.readFully(keyBytes);
        String key = new String(keyBytes, StandardCharsets.UTF_8);

        if (valueLength == tombstone) {
            return new Record(key, null);
        }

        byte[] valueBytes = new byte[valueLength];
        file.readFully(valueBytes);
        String value = new String(valueBytes, StandardCharsets.UTF_8);
        return new Record(key, value);
    }
}
