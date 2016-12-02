package MediaCollectie.data.heap;

import java.util.Collection;

public class MedianHeap<E> extends Heap<E>{
    private MinHeap<E> minHeap;
    private MaxHeap<E> maxHeap;

    public MedianHeap() {
        minHeap = new MinHeap<>();
        maxHeap = new MaxHeap<>();
    }

    public MedianHeap(Collection<E> list) {
        this();
        list.forEach(this::add);
    }

    @Override
    public E pop() {
        if(maxHeap.size() - minHeap.size() == 0) return maxHeap.pop();
        else return minHeap.pop();
    }

    @Override
    public E peak() {
        if(maxHeap.size() - minHeap.size() == 0) return maxHeap.peak();
        else return minHeap.peak();
    }

    @Override
    public void add(E object) {
        maxHeap.add(object);
        System.out.println(object);
        sortArray();
    }

    @Override
    public int size() {
        return minHeap.size() + maxHeap.size();
    }

    @Override
    protected void sortArray() {
        if(maxHeap.size() - minHeap.size() > 1) minHeap.add(maxHeap.pop());
    }
}