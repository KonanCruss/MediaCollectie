package MediaCollectie.util.sort;

import MediaCollectie.data.heap.MedianHeap;

import java.util.ArrayList;
import java.util.List;

public class SortMedian<E> implements ISort<E> {
    private List<E> toSortList;

    public SortMedian(List<E> list) {
        toSortList = list;
    }

    @Override
    public List<E> run() {
        MedianHeap<E> sortHeap = new MedianHeap<E>(toSortList);
        List<E> returnList = new ArrayList<E>(sortHeap.size());
        while(sortHeap.size() > 0) {
            E object = sortHeap.pop();
            System.out.println(object);
            returnList.add(object);
        }
        return returnList;
    }
}
