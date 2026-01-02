package org.example;

public class LogPointer {
    long offset;
    int size;

    public LogPointer(long offset, int size) {
        this.offset = offset;
        this.size = size;
    }
}
