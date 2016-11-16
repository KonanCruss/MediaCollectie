package com.apexTOP.util;

import com.apexTOP.data.MediaObject;

import java.util.ArrayList;

public class SearchClosest<E extends MediaObject> implements ISearch<E> {
    private ArrayList<E> searchList;

    private double lat;
    private double lon;

    public SearchClosest(double _lat, double _lon) {
        lat = _lat;
        lon = _lon;
    }

    @Override
    public boolean setList(ArrayList<E> list) {
        searchList = new ArrayList<>(list);
        return searchList.equals(list);
    }

    @Override
    public ArrayList<E> getList() {
        return searchList;
    }

    @Override
    public E run() {
        if(searchList == null) return null;
        E closest = null;
        double cDistance = Double.MAX_VALUE;

        for(E media : searchList) {
            double distance = HandlerLocation.geoDistance(lat, lon, media.getLatitude(), media.getLongitude());
            if(cDistance > distance) {
                cDistance = distance;
                closest = media;
            }
        }

        return closest;
    }
}
