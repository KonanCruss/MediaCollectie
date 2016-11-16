package MediaCollectie.util;

import MediaCollectie.data.MediaObject;

import java.util.ArrayList;

/**
 * Sorts a list based on the insertion sort algorithm.
 *
 * @param <E>
 */
public class SortInsertion<E extends MediaObject> implements ISort<E>{
    private ArrayList<E> sortList;

    public SortInsertion() {
        sortList = new ArrayList<E>();
    }

    /**
     * Sets the list that the sorting algorithm needs to sort.
     *
     * @param list List that needs to be sorted
     * @return True if the list is set successfully
     */
    @Override
    public boolean setList(ArrayList<E> list) {
        this.sortList = list;
        return list.equals(sortList);
    }

    /**
     * Returns the list which is/or isn't sorted.
     *
     * @return Sorted or To Sort list.
     */
    @Override
    public ArrayList<E> getList() {
        return sortList;
    }

    /**
     * Start the sorting algorithm.
     */
    @Override
    public void run() {
        int inner;
        E temp;
        for(int i = 1; i < sortList.size(); ++i) {
            temp = sortList.get(i);
            inner = i;
            while(inner > 0 && sortList.get(i - 1).getDatum().getTime() >= temp.getDatum().getTime()) {
                sortList.set(inner, sortList.get(inner - 1));
                --inner;
            }
            sortList.set(inner, temp);
        }
    }
}
