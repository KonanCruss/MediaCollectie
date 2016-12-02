package MediaCollectie.util.search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchDateBinary<E> implements ISearch<E>{
    private LocalDate searchDate = LocalDate.now();
    private HashMap<E, LocalDate> searchList;

    public SearchDateBinary(HashMap<E, LocalDate> list) {
        searchList = list;
    }
    public SearchDateBinary(HashMap<E, LocalDate> list, LocalDate date) {
        this(list);
        searchDate = date;
    }

    @Override
    public E run() {
        List<E> searchObjects = new ArrayList<E>(searchList.keySet());
        List<LocalDate> compareObjects = new ArrayList<>(searchList.values());
        if(searchDate.isBefore(compareObjects.get(0)) || searchDate.isAfter(compareObjects.get(compareObjects.size() - 1)))
            return null;

        int count = compareObjects.size() / 2;
        while(count > 0) {
            count = compareObjects.size() / 2;
            if(searchDate.isBefore(compareObjects.get(count))) {
                for(int i = 0; i < count; ++i) {
                    compareObjects.remove(count);
                    searchObjects.remove(count);
                }
            } else if(searchDate.isAfter(compareObjects.get(count))) {
                for(int i = 0; i < count; ++i) {
                    compareObjects.remove(0);
                    searchObjects.remove(0);
                }
            } else {
                return searchObjects.get(count);
            }
        }
        if(searchObjects.size() < 1) return null;
        else if(searchDate.isEqual(compareObjects.get(count))) return searchObjects.get(count);
        else return null;
    }
}
