package org.example;

import java.util.*;

public class KeyDir {
    private final Map<String, LogPointer> keyDir = new HashMap<>();

    public void put(String key, long recordStart, int valueSize) {
        keyDir.put(key, new LogPointer(recordStart, valueSize));
    }

    public LogPointer get(String key) {
        return keyDir.get(key);
    }

}
