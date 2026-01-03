package org.example;

public class LogPointer {
    public final long recordStart;
    public final int size;

    public LogPointer(long recordStart, int size) {
        this.recordStart = recordStart;
        this.size = size;
    }
}
