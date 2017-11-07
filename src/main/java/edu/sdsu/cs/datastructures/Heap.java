package edu.sdsu.cs.datastructures;

import edu.sdsu.cs.datastructures.CirArrayList;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
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
        Node node = new Node(e);
        if (items == 0) {
            // System.out.println(" OFFER -- SIZE: " + size() + " DATA: " + node.getData() + " ");
            // displayHeap();
            storage.add(0, node);
            items++;
            return true;
        }
        System.out.println(" OFFER -- SIZE: " + size() + " DATA: " + node.getData() + " ");
        displayHeap();
        storage.add(items, node);
        trickleUp(items++);

        return true;
    }

    public void trickleUp(int index) {
        int parentindex = (index - 1) / 2;
        Node parent = storage.get(parentindex);
        Node current = storage.get(index);

        while ((compare(parentindex, index) < 0) && index > 0) {
            if( ((Comparable<E>) storage.get(parentindex).getData()).compareTo(storage.get(index).getData()) == 0) {
                storage.get(index).increasePriority();
                break;
            } else {
                current = storage.get(index);
                parent = storage.get(parentindex);
    
                // System.out.println("trickle: index- " + index +  " data: " + storage.get(index).getData() + " parent-" + parentindex +  " Parent data: " + storage.get(parentindex).getData() + " size- " + items);
                // System.out.println("swapping");
                // displayHeap();
                storage.set(parentindex, current);
                storage.set(index, parent);
                index = parentindex;
                parentindex = (index - 1) / 2;
            }
        }
    }

    private int compare(int i1, int i2) {
        return ((Comparable<E>) storage.get(i1).getData()).compareTo(storage.get(i2).getData());
    }

    private boolean hasParent(int i) {
        return i > 1;
    }

    private int parentIndex(int i) {
        return i / 2;
    }

    /***
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E poll() {
        System.out.print("POLL: " + items + " DATA: " + storage.get(0).getData());
        displayHeap();
        Node root = storage.get(0);
        storage.set(0, storage.get(--items));
        trickleDown(0);
        return (E) root.getData();
    }

    public void trickleDown(int index) {
        int largerChild;
        Node top = storage.get(index); // save root
        while (index < items / 2) // while node has at
        { //    least one child,
            Node temp = storage.get(index);
            System.out.println("trickledown: index- " + index +  " data: " + storage.get(index).getData() + " child-" + (2 * index + 1) +  " Child data: " + storage.get(2 * index + 1).getData() + " size- " + items);
            displayHeap();
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;
            if (rightChild < items && (compare(leftChild, rightChild) < 0)){
                largerChild = rightChild;
            } else {
                largerChild = leftChild;
            }
            if (compare(index, largerChild) >= 0) {
                break;
            }
            storage.set(index, storage.get(largerChild));
            storage.set(largerChild, temp);
            index = largerChild;
        }
        storage.set(index, top);
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

    public void displayHeap() {
        for (Node var : storage) {
            System.out.print(var.getData() + " ");
        } // array format
        // for(int m=0; m<size(); m++)
        //    if(storage.get(m) != null)
        //       System.out.print( storage.get(m).getData() + " ");
        //    else
        //       System.out.print( "-- ");
        System.out.println();
        // heap format
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0; // current item
        String dots = "...............................";
        System.out.println(dots + dots); // dotted top line

        while (size() > 0) // for each heap item
        {
            if (column == 0) // first item in row?
                for (int k = 0; k < nBlanks; k++) // preceding blanks
                    System.out.print(' ');
            // display item
            System.out.print(storage.get(j).getData() + "[" + j + "] ");

            if (++j == size()) // done?
                break;

            if (++column == itemsPerRow) // end of row?
            {
                nBlanks /= 2; // half the blanks
                itemsPerRow *= 2; // twice the items
                column = 0; // start over on
                System.out.println(); //    new row
            } else // next item on row
                for (int k = 0; k < nBlanks * 2 - 2; k++)
                    System.out.print(' '); // interim blanks
        } // end for
        System.out.println("\n" + dots + dots); // dotted bottom line
    } // end displayHeap()
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