package com.apexTOP.util;

import com.apexTOP.data.MediaObject;
import com.apexTOP.data.MedianHeap;

import java.util.ArrayList;

public class SortLightIntensity<E extends MediaObject> implements ISort<E> {
    private ArrayList<E> sortList;
    private MedianHeap sortHeap;

    public SortLightIntensity() {
        sortList = new ArrayList<>();
        sortHeap = new MedianHeap();
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

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        if(sortList.isEmpty()) return;

        for (E media : sortList) {
            sortHeap.add(media);
        }
        ArrayList<E> sortListTemp = new ArrayList<>();
        while (sortHeap.getLength() > 0) {
            sortListTemp.add((E) sortHeap.pop());
        }
        sortList = sortListTemp;
    }
}
