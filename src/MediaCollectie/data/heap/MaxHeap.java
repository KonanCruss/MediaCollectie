package MediaCollectie.data.heap;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

public class MaxHeap<E> extends Heap<E> {
    public MaxHeap() {
        data = new ArrayList<>();
    }

    public MaxHeap(Collection<E> list) {
        data = new ArrayList<>();
        list.forEach(this::add);
    }

    @Override
    protected void sortArray() {
        if (data.size() < 2) return;
        // Children = 2i (+1) of parent i
        if(data.get(0) instanceof Comparable) {
            if(added) {
                int counter = data.size() - 1;
                boolean even = counter % 2 == 0;
                while (counter > 0) {
                    if (even && ((Comparable) data.get((counter - 2) / 2)).compareTo(data.get(counter)) != 1) {
                        E temp = data.remove(counter);
                        E temp2 = data.remove((counter - 2) / 2);
                        data.add((counter - 2) / 2, temp);
                        data.add(counter, temp2);
                        counter = (counter - 2) / 2;
                    } else if (!even && ((Comparable) data.get((counter - 1) / 2)).compareTo(data.get(counter)) != 1) {
                        E temp = data.remove(counter);
                        E temp2 = data.remove((counter - 1) / 2);
                        data.add((counter - 1) / 2, temp);
                        data.add(counter, temp2);
                        counter = (counter - 1) / 2;
                    } else return;
                }
            } else {
                int counter = 0;
                while(counter < data.size()) {
                    int checkSum = counter;
                    if(!(data.size() > 2 * counter + 1)) return;
                    else if(data.size() < 2 * counter + 2) {
                        checkSum = 2 * counter + 1;
                    } else {
                        if(data.size() > 2 * counter + 2 && ((Comparable) data.get(2 * counter + 2)).compareTo(data.get(2 * counter + 1)) == 1) {
                            checkSum = 2 * counter + 2;
                        } else {
                            checkSum = 2 * counter + 1;
                        }
                    }
                    if(((Comparable) data.get(counter)).compareTo(data.get(checkSum)) != 1) {
                        E temp = data.remove(checkSum);
                        E temp2 = data.remove(counter);
                        data.add(counter, temp);
                        data.add(checkSum, temp2);
                        counter = checkSum;
                    } else return;
                }
            }
        } else
            throw new NotImplementedException();
    }
}
