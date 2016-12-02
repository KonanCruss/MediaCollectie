package MediaCollectie.data.heap;

import java.util.List;

public abstract class Heap<E> {
    protected List<E> data;
    protected boolean added;

    public E pop() {
        E returnValue = data.remove(0);
        //System.out.println("Test " + returnValue);
        if(data.size() > 1) {
            data.add(0, data.remove(data.size() - 1));
            added = false;
            sortArray();
        }
        //System.out.println("TABLE: " + size());
        //printTable();
        //System.out.println();
        return returnValue;
    }
    public E peak() {
        return data.get(0);
    }
    public void add(E object) {
        data.add(object);
        added = true;
        sortArray();
    }
    public int size() {
        return data.size();
    }
    public void printTable() {
        data.forEach(System.out::println);
    }

    protected abstract void sortArray();
}
