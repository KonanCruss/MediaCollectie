package MediaCollectie.util;

import MediaCollectie.data.MediaObject;

import java.util.ArrayList;

/**
 * Sort a depending list about any extended <class>MediaObject</class> Object.
 * It is possible to get the whole sorted list back by calling <method>this.getList()</method>,
 * after it is sorted.
 *
 * @param <E>
 */
public class SortDatum<E extends MediaObject> implements ISort<E> {
    private ArrayList<E> list;

    public SortDatum() {
        list = new ArrayList<>();
    }


    /**
     * Sets the list that the sorting algorithm needs to sort.
     *
     * @param list List that needs to be sorted
     * @return True if the list is set successfully
     */
    @Override
    public boolean setList(ArrayList<E> list) {
        this.list = list;
        return list.equals(this.list);
    }

    /**
     * Returns the list which is/or isn't sorted.
     *
     * @return Sorted or To Sort list.
     */
    @Override
    public ArrayList<E> getList() {
        return list;
    }

    /**
     * Start the sorting algorithm.
     */
    @Override
    public void run() {
        if(!list.isEmpty()) {
            ArrayList<E> tempList = new ArrayList<E>();
            tempList.add(list.get(0));
            for(int i = 1; i < list.size(); i++) {
                for(int j = 0; j < tempList.size(); j++)
                    if(tempList.get(j).getDatum().getTime() >= list.get(i).getDatum().getTime()) {
                        tempList.add(j, list.get(i));
                        break;
                    }
            }
        }
    }
}