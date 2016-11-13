package com.apexTOP.data;

public interface IHeap<E> {
    void add(MediaObject object);
    MediaObject pop();
    MediaObject peak();
    int getSize();
    int getLength();
}
