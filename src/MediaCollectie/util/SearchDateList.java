package MediaCollectie.util;

import MediaCollectie.data.MediaObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Searches through a list for all the matches decided by the date variable.
 * All the matches are stored in a list accessable from <method>this.getList()</method>
 * while <method>this.run()</method> returns the first match found.
 *
 * @param <E>
 */
public class SearchDateList<E extends MediaObject> extends SearchDate<E> {
    /**
     * Making a SearchDate object which search for all the matching objects.
     * It searches based on the given <parameter>date</parameter>.
     * Has all the matches stored in the list gotten by <method>this.getList()</method>
     * and returns the first match from <method>this.run()</method>.
     *
     * @param date Date on which you want matches to be found.
     */
    public SearchDateList(Date date) {
        super(date);
    }

    /**
     * Searches for all the specific matches in a list. Sets the original list
     * to be the list containing all the matches that have been found.
     * To get the original list use <method>this.getList()</method>.
     *
     * @return First found match.
     */
    @Override
    public E run() {
        ArrayList<E> tempList = new ArrayList<E>();

        // Cases on which we don't need to start searching:
        if(list.get(0).getDatum().getTime() > controlDate.getTime() || list.get(list.size() - 1).getDatum().getTime() < controlDate.getTime())
            return null;

        while(true) {
            // Done searching!
            if(list.size() <= 1)
                break;

            // Binary search:
            int i = list.size() % 2;
            if(list.get(i).getDatum().getTime() == controlDate.getTime())
                tempList.add(list.get(i));                                  // Adds the match to a temp List.
            if(list.get(i).getDatum().getTime() > controlDate.getTime())
                while(list.size() > i) list.remove(i);                      // Deletes all the variables after index i.
            if(list.get(i).getDatum().getTime() < controlDate.getTime())
                while(list.size() > i) list.remove(0);                      // Deletes all the variables before index i.
        }

        list = tempList;                                                    // Sets all the matches to the original stored list.
        return tempList.get(0);                                             // Return the first match.
    }
}
