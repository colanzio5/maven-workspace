package edu.sdsu.cs.datastructures;

import edu.sdsu.cs.datastructures.CirArrayList;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * A heap data structure implementing the priority queue interface.
 * <p>An efficient, array-based priority queue data structure.
 * </p>
 *
 * @author colin Casazza, CSSC0236
 */
public final class Heap<E> extends AbstractQueue<E> implements Queue<E> {
    final Comparator<E> comp;
    final CirArrayList<Element<E>> storage;
    private int items = 0;

    //CONSTRUCTORS
    /***
     * The collection constructor generates a new heap from the existing
     * collection using the enclosed item's natural ordering. Thus, these
     * items must support the Comparable interface.
     * @param col
     */
    public Heap(Collection<? extends E> col) {
        this();
        for (E e : col)
            offer(e);
    }

    /***
     * The default constructor generates a new heap using the natural order
     * of the objects inside. Consequently, all objects placed in this
     * container must implement the Comparable interface.
     */
    public Heap() {
        comp = (Comparator<E>) Comparator.naturalOrder();
        storage = new CirArrayList<Element<E>>();
        items = 0;
    }

    /***
     * Generates a new Heap from the provided collection using the specified
     * ordering. This allows the user to escape the natural ordering or
     * provide one in objects without.
     * @param col the collection to use
     * @param orderToUse the ordering to use when sorting the heap
     */
    public Heap(Collection<? extends E> col, Comparator<E> orderToUse) {
        this(orderToUse);
        for (E e : col)
            offer(e);
        items = col.size();
    }

    /***
     * Generates a new, empty heap using the Comparator object provided as
     * its parameter. Thus, items in this heap possess no interface
     * requirements.
     *
     * @param orderToUse
     */
    public Heap(Comparator<E> orderToUse) {
        comp = orderToUse;
        storage = new CirArrayList<>();
        items = 0;
    }

    //PUBLIC METHODS
    /***
     * An IN-PLACE sort function using heapsort.
     *
     * @param data a list of data to sort
     */
    public static <T> void sort(List<T> data) {
        Heap<T> temp = new Heap<>();
        for (T e : data)
            temp.offer(e);
        for (int i = 0; i < data.size(); i++)
            data.set(i, (T) temp.poll());
    }

    /***
     * An IN-PLACE sort function using heapsort.
     *
     * @param data a list of data to sort
     * @param order the comparator object expressing the desired order
     */
    public static <T> void sort(List<T> data, Comparator<T> order) {
        Heap<T> temp = new Heap<>(order);
        for (T e : data)
            temp.offer(e);
        for (int i = 0; i < data.size(); i++)
            data.set(i, (T) temp.poll());
    }

    /**
     * The iterator in this assignment provides the data to the user in heap
     * order. The lowest element will arrive first, but that is the only real
     * promise.
     *
     * @return an iterator to the heap
     */
    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) new HeapIterator();
    }

    /***
     * Provides the caller with the number of items currently inside the heap.
     * @return the number of items in the heap.
     */
    @Override
    public int size() {
        return items;
    }

    /***
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions. Heaps
     * represent priority queues, so the first element in the queue must
     * represent the item with the lowest ordering (highest priority).
     *
     * @param e element to offer the queue
     * @return
     */
    @Override
    public boolean offer(E e) {
        Element<E> el = (Element<E>) new Element(e);
        storage.add(items, el);
        trickleUp(items++);
        return true;
    }

    /***
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E poll() {
        E ret = null;
        if (items == 0)
            return null;
        else {
            ret = storage.get(0).getData();
            storage.set(0, storage.get(--items));
            storage.set(items, null);
            if (items > 0)
                trickleDown(0);
            return (E) ret;
        }
    }

    /***
    * Retrieves, but does not remove, the head of this queue, or returns
    * null if this queue is empty.
    * @return the head of this queue, or null if this queue is empty
    */
    @Override
    public E peek() {
        if (items == 0)
            return null;
        return (E) storage.get(0).getData();
    }

    private void trickleUp(int index) {
        int parentindex = (index - 1) / 2;
        Element<E> parent = storage.get(parentindex);
        Element<E> current = storage.get(index);
        Element<E> last = current;
        while (index > 0 && (compare(parentindex, index) < 0)) {
            if (compare(parentindex, index) == 0)
                break;
            else {
                current = storage.get(index);
                parent = storage.get(parentindex);
                storage.set(parentindex, current);
                storage.set(index, parent);
                index = parentindex;
                parentindex = (index - 1) / 2;
            }
            storage.set(index, last);
        }
    }

    public void trickleDown(int index) {
        int largest;
        while (index < (items) / 2) {
            Element<E> temp = storage.get(index);
            int left = 2 * index + 1;
            int rightChild = left + 1;
            if (rightChild + 1 > items)
                break;
            if (rightChild < items && (compare(left, rightChild) > 0))
                largest = left;
            else
                largest = rightChild;
            try {
                if (compare(index, largest) == 0)
                    break;
            } catch (Exception e) {
                throw new IndexOutOfBoundsException();
            }
            storage.set(index, storage.get(largest));
            storage.set(largest, temp);
            index = largest;
        }
    }

    //PRIVATE METHODS
    //int1 - parent, int2 - child
    private int compare(int i1, int i2) {
        int ret = 0;
        try {
            Comparable<E> e1 = ((Comparable<E>) storage.get(i2).getData());
            Comparable<E> e2 = ((Comparable<E>) storage.get(i1).getData());
            ret = comp.compare((E) (Comparable<E>) storage.get(i2).getData(),
                    (E) (Comparable<E>) storage.get(i1).getData());
            if (ret == 0) {
                ret = storage.get(i2).getPriority() - storage.get(i1).getPriority();
                if(ret == 0)
                    System.out.println("INCREASING PRIORITY");
                    storage.get(i1).setPriority(storage.get(i2).getPriority() + 1);
            }
        } catch (Exception e) {
            return ret;
        } 
        return ret; 
    }

    /**
    UTILITY FUNCTION 
        - TAKEN FROM http://homepage.divms.uiowa.edu/~sriram/21/spring07/code/heap.java
        - was modified to fit case needs.
        - displays heap in binary tree with index and priority
        - pure - can keep public??
    */
    public void displayHeap() {
        for (Element<E> var : storage)
            System.out.print(var.getData() + " ");
        System.out.println();

        int nBlanks = 64;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;
        String dots = "...............................";
        System.out.println(dots + dots);
        while (size() > 0) {
            if (column == 0)
                for (int k = 0; k < nBlanks; k++)
                    System.out.print(' ');

            System.out.print(" D: " + storage.get(j).getData() + " P: " + storage.get(j).getPriority());
            if (++j == size())
                break;
            if (++column == itemsPerRow) {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            } else
                for (int k = 0; k < nBlanks * 2 - 2; k++)
                    System.out.print(' ');
        } // end for
        System.out.println("\n" + dots + dots);
    }

    //INNER PRIVATE CLASSES
    //Provides a Iterator for Keys in Heap Collection
    private class HeapIterator<E> implements Iterator<E> {
        private int cursor;

        public HeapIterator() {
            this.cursor = 0;
        }

        public boolean hasNext() {
            return this.cursor < items;
        }

        public E next() {
            if (this.hasNext()) {
                int current = cursor;
                cursor++;
                return (E) storage.get(current).getData();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
} //END HEAP CLASS

//WRAPPER CLASS
class Element<E> {
    private E value;
    private int priority = 0;

    public Element(E data) {
        value = (E) data;
    }

    public E getData() {
        return (E) this.value;
    }

    public void setData(E data) {
        this.value = data;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void increasePriority() {
        this.priority++;
    }
}