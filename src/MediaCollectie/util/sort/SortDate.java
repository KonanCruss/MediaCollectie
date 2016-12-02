package MediaCollectie.util.sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SortDate<E> implements ISort<E> {
    private HashMap<E, LocalDate> toSortMap;

    public SortDate(HashMap<E, LocalDate> sortMap) {
        toSortMap = sortMap;
    }

    @Override
    public List<E> run() {
        List<E> returnObjects = new ArrayList<E>();
        List<E> sortObjects = new ArrayList<E>(toSortMap.keySet());

        List<LocalDate> sortedObjects = new ArrayList<>();
        List<LocalDate> compareObjects = new ArrayList<>(toSortMap.values());

        int counter = 0;
        while(sortedObjects.size() < compareObjects.size()) {
            LocalDate compareDate = compareObjects.get(counter);
            if(sortedObjects.isEmpty()) {
                sortedObjects.add(compareDate);
                returnObjects.add(sortObjects.get(counter));
            } else {
                int i = 0;
                while(i < sortedObjects.size() && !compareDate.isBefore(sortedObjects.get(i))) ++i;
                sortedObjects.add(i, compareDate);
                returnObjects.add(i, sortObjects.get(counter));
            }
            ++counter;
        }

        return returnObjects;
    }
}
