package com.apexTOP.util;

import java.util.ArrayList;

public interface ISort<E> {
    boolean setList(ArrayList<E> list);
    ArrayList<E> getList();
    void run();
}
