package MediaCollectie.data;

import java.util.Arrays;

/**
 * Heap structure for big numbers. The minimum is set in the root.
 */
public class MinHeap implements IHeap {
    private MediaObject[] storeArray;
    private int index = 0;
    private int capacity = Byte.MAX_VALUE;

    /**
     * Makes a MinHeap with a set <param>initialCapacity</param>.
     *
     * @param initialCapacity Capacity of the Heap when first initialized.
     */
    public MinHeap(int initialCapacity) {
        storeArray = new MediaObject[initialCapacity];
        capacity = initialCapacity;
    }

    /**
     * Makes a MinHeap with a initial capacity of 255.
     */
    public MinHeap() {
        storeArray = new MediaObject[capacity];
    }

    /**
     * Adds a element <param>e</param> to the Heap.
     *
     * @param e Element to be added.
     */
    @Override
    public void add(MediaObject e) {
        if(storeArray[storeArray.length - 1] != null)
            storeArray = Arrays.copyOf(storeArray, storeArray.length + (storeArray.length << 1));
        storeArray[index] = e;
        index++;
        sortArray();
    }

    /**
     * Removes and returns the root of the Heap.
     *
     * @return The root.
     */
    @SuppressWarnings("unchecked")
    @Override
    public MediaObject pop() {
        if(index == 0)
            return null;

        MediaObject var = storeArray[0];
        storeArray[0] = null;

        sortArray();

        return var;
    }

    /**
     * Shows which value is set on the root.
     *
     * @return The root.
     */
    @SuppressWarnings("unchecked")
    @Override
    public MediaObject peak() {
        return storeArray[0];
    }

    /**
     * Gets the size of the reserved memory.
     *
     * @return The size of the reserved memory of the array.
     */
    @Override
    public int getSize() {
        return capacity;
    }

    /**
     * Gets the amount of objects are stored.
     *
     * @return Amount of objects stored.
     */
    @Override
    public int getLength() {
        return index;
    }

    /**
     * Sorts the Heap so the root always has the minimum.
     */
    private void sortArray() {
        if(index == 0) return;
        if(storeArray[0] == null) {
            index--;
            storeArray[0] = storeArray[index];
            storeArray[index] = null;

            // Putting the root on the right place:
            int i = 0;
            while(true) {
                if(storeArray[i] == null) break;
                if(storeArray[i].getGreyScaleMean() > storeArray[2*i+1].getGreyScaleMean()) {
                    MediaObject tempVar = storeArray[i];
                    storeArray[i] = storeArray[2*i+1];
                    storeArray[2*i+1] = tempVar;

                    i = 2*i+1;
                } else if(storeArray[i].getGreyScaleMean() > storeArray[2*i+2].getGreyScaleMean()) {
                    MediaObject tempVar = storeArray[i];
                    storeArray[i] = storeArray[2*i+2];
                    storeArray[2*i+2] = tempVar;

                    i = 2*i+2;
                } else break;
            }
        } else {
            int i = index - 1;
            while(true) {
                if(i == 0) break;
                if(i%2 != 0) {
                    if(storeArray[i].getGreyScaleMean() < storeArray[(i-1)/2].getGreyScaleMean()) {
                        MediaObject tempVar = storeArray[i];
                        storeArray[i] = storeArray[(i-1)/2];
                        storeArray[(i-1)/2] = tempVar;

                        i = (i-1)/2;
                    } else break;
                } else {
                    if(storeArray[i].getGreyScaleMean() < storeArray[(i-2)/2].getGreyScaleMean()) {
                        MediaObject tempVar = storeArray[i];
                        storeArray[i] = storeArray[(i-2)/2];
                        storeArray[(i-2)/2] = tempVar;

                        i = (i-2)/2;
                    } else break;
                }
            }
        }
    }
}
