package com.apexTOP.data;

/**
 * Heap structure that uses a <class>MinHeap</class> and <class>MaxHeap</class> to get the
 * median value in the roots of the <class>MinHeap</class> and <class>MaxHeap</class>.
 */
public class MedianHeap implements IHeap {
    private MinHeap minHeap;
    private MaxHeap maxHeap;

    /**
     * Makes a MedianHeap of which both the Min and MaxHeap have a starting capacity of 255.
     */
    @SuppressWarnings("unchecked")
    public MedianHeap() {
        minHeap = new MinHeap();
        maxHeap = new MaxHeap();
    }

    /**
     * Makes a MedianHeap of which both the Min and MaxHeap have a set <param>initialCapacity</param>.
     *
     * @param initialCapacity Starting capacity.
     */
    @SuppressWarnings("unchecked")
    public MedianHeap(int initialCapacity) {
        minHeap = new MinHeap(initialCapacity);
        maxHeap = new MaxHeap(initialCapacity);
    }

    /**
     * Adds a element <param>e</param> to the Heap.
     *
     * @param e Element to be added.
     */
    @Override
    public void add(long e) {
        minHeap.add(e);
        sortHeaps();
    }

    /**
     * Removes and returns the root of the Heap. It switches between the MinHeap and MaxHeap.
     *
     * @return The root.
     */
    @Override
    public long pop() {
        long returnVar;
        if(minHeap.getLength() <= maxHeap.getLength())
            returnVar = maxHeap.pop();
        else
            returnVar = minHeap.pop();
        sortHeaps();

        return returnVar;
    }

    /**
     * Shows which value is set on the root. It switches between the MinHeap and MaxHeap.
     *
     * @return The root.
     */
    @Override
    public long peak() {
        if(minHeap.getLength() == maxHeap.getLength())
            return maxHeap.peak();
        else
            return minHeap.peak();
    }

    /**
     * Gets the size of the reserved memory.
     *
     * @return The size of the reserved memory of the array.
     */
    @Override
    public int getSize() {
        return minHeap.getSize() + maxHeap.getSize();
    }

    /**
     * Gets the amount of objects are stored.
     *
     * @return Amount of objects stored.
     */
    @Override
    public int getLength() {
        return minHeap.getLength() + maxHeap.getLength();
    }

    /**
     * Sorts the heap so the MinHeap doesn't have more than 1 variable compared to the MaxHeap.
     */
    private void sortHeaps() {
        if(!(minHeap.getLength() - maxHeap.getLength() > 2))
            return;
        maxHeap.add(minHeap.pop());
    }
}
