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
    final CirArrayList<Node<E>> storage;
    private int items = 0;

    /***
     * The collection constructor generates a new heap from the existing
     * collection using the enclosed item's natural ordering. Thus, these
     * items must support the Comparable interface.
     * @param col
     */
    public Heap(Collection<? extends E> col) {
        this();
        for (E e : col) {
            offer(e);
        }
    }

    /***
     * The default constructor generates a new heap using the natural order
     * of the objects inside. Consequently, all objects placed in this
     * container must implement the Comparable interface.
     */
    public Heap() {
        comp = (Comparator<E>) Comparator.naturalOrder();
        storage = new CirArrayList<Node<E>>();
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
        for (E e : col) {
            offer(e);
        }
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
        Node<E> node = (Node<E>) new Node(e);
        storage.add(items, node);
        trickleUp(items++);
        return true;
    }
    public void trickleUp(int index) {
        int parentindex = (index - 1) / 2;
        Node<E> parent = storage.get(parentindex);
        Node<E> current = storage.get(index);
        Node<E> bottom = current;
        while (index > 0 && (compare(parentindex, index) < 0)) {
            if (compare(parentindex, index) == 0) {
                storage.get(index).increasePriority();
                break;
            } else {
                current = storage.get(index);
                parent = storage.get(parentindex);
                storage.set(parentindex, current);
                storage.set(index, parent);
                index = parentindex;
                parentindex = (index - 1) / 2;
            }
            storage.set(index, bottom);
        }
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
    public void trickleDown(int index) {
        int largerChild;
        while (index < (items) / 2) {
            Node<E> temp = storage.get(index);
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;
            if (rightChild + 1 > items)
                break;
            if (rightChild < items && (compare(leftChild, rightChild) > 0))
                largerChild = leftChild;
            else
                largerChild = rightChild;
            try {
                if (compare(index, largerChild) == 0)
                    break;
            } catch (Exception e) {
                throw new IndexOutOfBoundsException();
            }
            storage.set(index, storage.get(largerChild));
            storage.set(largerChild, temp);
            index = largerChild;
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

    //PRIVATE CLASSES
    private int compare(int i1, int i2) {
        try {
            Comparable<E> e1 = ((Comparable<E>) storage.get(i2).getData());
            Comparable<E> e2 = ((Comparable<E>) storage.get(i1).getData());
            return comp.compare((E) (Comparable<E>) storage.get(i2).getData(),
                    (E) (Comparable<E>) storage.get(i1).getData());
        } catch (Exception e) {
            return 0;
        }
    }
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
}

class Node<E> {
    private E value;
    private int index;
    private int priority;

    public Node(E data, int i) {
        value = (E) data;
        this.index = i;
    }

    public Node(E data) {
        value = (E) data;
    }

    public int getIndex() {
        return this.index;
    }

    public E getData() {
        return (E) this.value;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setData(E data) {
        this.value = data;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(E data) {
        this.value = data;
    }

    public void increasePriority() {
        this.priority++;
    }
}