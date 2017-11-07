package edu.sdsu.cs.datastructures;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
/**
 * A circular version of an array list.
 * <p>Operates as a standard java.util.ArrayList, but additions and removals
 * from the List's front occur in constant time, for its circular nature
 * eliminates the shifting required when adding or removing elements to the
 * front of the ArrayList.
 * </p>
 *
 * @author STUDENT NAME HERE, csscXXXX
 */
public final class CirArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess {
    private E[] storage, temp;
    private int number_items, head, tail;
    /**
     * Builds a new, empty CirArrayList.
     */
    public CirArrayList() {
        // todo: default constructor
        storage = (E[]) new Object[16];
        number_items = 0;
        head = 0;
        tail = 0;
    }
    /**
     * Constructs a new CirArrayList containing all the items in the input
     * parameter.
     *
     * @param col the Collection from which to base
     */
    public CirArrayList(Collection<? extends E> col) {

        this.storage = (E[]) col.toArray(new Object[col.size() + 16]);
        this.number_items = col.size();
        this.head = col.size();
        this.tail = 0;
    }
    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index (0 based) of the element to return.
     * @return element at the specified position in the list.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0
     *                                   || index >= size())
     */
    @Override
    public E get(int index) {
        E e = null;
        if(index >= number_items){
            throw new IndexOutOfBoundsException();
        } else {
            e = storage[((tail + index) % storage.length)];
        } return (E)e;
    }
    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index index of the element to replace
     * @param value element to be stored at teh specified position
     * @return element previously at the specified position
     * @throws IndexOutOfBoundsException if index is out of the range (index < 0
     *                                   || index >= size())
     */
    @Override
    public E set(int index, E value) {
        E e = null;
        if ((index < 0) || (index >= size()))
            throw new IndexOutOfBoundsException();
        else {
            e = storage[((tail + index) % storage.length)];
            storage[((tail + index) % storage.length)] = value;
        } return (E) e;
    }
    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param value element to be inserted
     */
    @Override
    public void add(int index, E value) {
        if ((index < 0) || (index > number_items)){
            increaseBufferSize();
            this.add(index, value);
        }
        if ((head + 1) % storage.length == tail) {
            increaseBufferSize();
            this.add(index, value);
        } else {
            for (int i = (number_items + 1) % storage.length; i > index; i--)
                storage[(tail + i) % storage.length] = storage[(tail + i - 1) % storage.length];
            number_items++;
            head = (head + 1) % storage.length;
            storage[(tail + index) % storage.length] = (E) value;
        }
    } /** private method for expanding the buffer size when full */
    private void increaseBufferSize() {
        temp = (E[]) new Object[storage.length * 2];
        for (int i = 0; i < number_items; i++)
            temp[i] = storage[(tail + i) % storage.length];
        head = number_items;
        tail = 0;
        storage = temp;
    }
    /**
     * Removes the element at the specified position in this list.  Shifts
     * any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index index of element to remove
     * @return the element previously at the specified position.
     */
    @Override
    public E remove(int index) {
        E e = null;
        if ((index < 0) || (index >= number_items))
            throw new IndexOutOfBoundsException("REMOVE_INVALID_INDEX: " + index);
        else {
            e = storage[(tail + index) % storage.length];
            if (index == 0) {
                storage[(tail + index) % storage.length] = null;
                tail = (tail + 1) % storage.length;
            } else {
                for (int i = 0; i <= (number_items - index); i++)
                    storage[((tail + i + index) % storage.length)] = storage[((tail + i + index + 1) % storage.length)];
                storage[(head) % storage.length] = null;
                head = (head - 1) % storage.length;
            }
            number_items--;
        } return (E) e;
    }
    /**
     * Reports the number of items in the List.
     *
     * @return the item count.
     */
    @Override
    public int size() {
        return number_items;
    }
}