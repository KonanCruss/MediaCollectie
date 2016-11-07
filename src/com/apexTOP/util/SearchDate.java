package com.apexTOP.util;

import com.apexTOP.data.MediaObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Searches through a list for the first match decided by the date variable.
 *
 * @param <E>
 */
public class SearchDate<E extends MediaObject> implements ISearch<E> {
    ArrayList<E> list;
    Date controlDate;

    /**
     * Making a SearchDate searching object to get the first match.
     *
     * @param date Date on which you want a match to be found.
     */
    public SearchDate(Date date) {
        list = new ArrayList<>();
        controlDate = date;
    }

    /**
     * Sets the list from which there needs to be searched from.
     *
     * @param list List from which there needs to be data searched.
     * @return True if the list was set successfully.
     */
    @Override
    public boolean setList(ArrayList<E> list) {
        this.list = list;
        return list.equals(this.list);
    }

    /**
     * Getting the list from which there needs to be searched.
     *
     * @return List to search through.
     */
    @Override
    public ArrayList<E> getList() {
        return list;
    }

    /**
     * Runs the sorting algorithm to find 1 MediaObject with the same Date.
     *
     * @return The first match.
     */
    @Override
    public E run() {
        // Cases on which we don't need to start searching:
        if(list.get(0).getDatum().getTime() > controlDate.getTime() || list.get(list.size() - 1).getDatum().getTime() < controlDate.getTime())
            return null;

        while(true) {
            // No matches found!
            if(list.size() <= 1)
                return null;

            // Binary search:
            int i = list.size() % 2;
            if(list.get(i).getDatum().getTime() == controlDate.getTime())
                return list.get(i);                                             // Match found.
            if(list.get(i).getDatum().getTime() > controlDate.getTime())
                while(list.size() > i) list.remove(i);                          // Remove every variable after the index i.
            if(list.get(i).getDatum().getTime() < controlDate.getTime())
                while(list.size() > i) list.remove(0);                          // Remove every variable before the index i.
        }
    }
}
