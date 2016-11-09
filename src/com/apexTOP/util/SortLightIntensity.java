package com.apexTOP.util;

import com.apexTOP.data.MediaObject;

import java.util.ArrayList;

public class SortLightIntensity<E extends MediaObject> implements ISort<E> {
    private ArrayList<E> sortList;
    public SortLightIntensity() {
        sortList = new ArrayList<>();
    }

    @Override
    public boolean setList(ArrayList<E> list) {
        sortList = list;
        return sortList.equals(list);
    }

    @Override
    public ArrayList<E> getList() {
        return sortList;
    }

    @Override
    public void run() {

    }
}
