package edu.sdsu.cs.datastructures;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * A heap data structure implementing the priority queue interface.
 * <p>An efficient, array-based priority queue data structure.
 * </p>
 *
 * @author STUDENT NAME HERE, csscXXXX
 */

public final class Heap<E> extends AbstractQueue<E> implements Queue<E> {

    final Comparator<E> comp;
    final List<E> storage;

    /***
     * The collection constructor generates a new heap from the existing
     * collection using the enclosed item's natural ordering. Thus, these
     * items must support the Comparable interface.
     * @param col
     */
    public Heap(Collection<? extends E> col) {
        this();
    }

    /***
     * The default constructor generates a new heap using the natural order
     * of the objects inside. Consequently, all objects placed in this
     * container must implement the Comparable interface.
     */
    public Heap() {
        comp = (Comparator<E>) Comparator.naturalOrder();
        storage = new java.util.ArrayList<>();
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
        storage = new java.util.ArrayList<>();
    }

    /***
     * An IN-PLACE sort function using heapsort.
     *
     * @param data a list of data to sort
     */
    public static <T> void sort(List<T> data) {

    }

    /***
     * An IN-PLACE sort function using heapsort.
     *
     * @param data a list of data to sort
     * @param order the comparator object expressing the desired order
     */
    public static <T> void sort(List<T> data, Comparator<T> order) {

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
        return null;
    }

    /***
     * Provides the caller with the number of items currently inside the heap.
     * @return the number of items in the heap.
     */
    @Override
    public int size() {
        return 0;
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
        return false;
    }

    /***
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E poll() {
        return null;
    }

    /***
     * Retrieves, but does not remove, the head of this queue, or returns
     * null if this queue is empty.
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E peek() {
        return null;
    }

}
