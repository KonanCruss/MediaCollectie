package com.apexTOP.data;

public interface IHeap {
    void add(long object);
    long pop();
    long peak();
    int getSize();
    int getLength();
}
