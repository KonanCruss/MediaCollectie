package com.apexTOP.util;

import java.util.ArrayList;

public interface ISearch<E> {
    boolean setList(ArrayList<E> list);
    ArrayList<E> getList();
    E run();
}
