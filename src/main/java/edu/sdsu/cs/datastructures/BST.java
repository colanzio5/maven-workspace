
package edu.sdsu.cs.datastructures;

import edu.sdsu.cs.datastructures.Node;
import java.util.*;

/**
* Assignment 4: HashMap and BinarySearchTree
* 
* @author Colin Casazza, cssc0236
* branched https://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/code/BST.java
* implemented MapADT, Iterators and Ordering, Modified Getter Methods
*/
public class BST<K extends Comparable<K>, V> implements MapADT<K, V> {

    private Node<K, V> root;
    private int size = 0;

    public BST() {
        root = null;
    }

    /**
    * Returns true if the map has an object for the corresponding key.
    * @param key object to search for
    * @return true if within map, false otherwise
    */
    @Override
    public boolean contains(K key) {
        return findValue(root, key) != null;
    }

    /**
    * Adds the given key/value pair to the map.
    * @param key Key to add to the map
    * @param value Corresponding value to associate with the key
    * @return the previous value associated with this key or null if new
    */
    @Override
    public V add(K key, V value) {
        V ret = getValue(key);
        root = insert(root, key, value);
        size++;
        return ret;
    }

    /**
    * Removes the key/value pair identified by the key parameter from the map.
    * @param key item to remove
    * @return true if removed, false if not found or unable to remove
    */
    @Override
    public boolean delete(K key) {
        try {
            deleteKey(root, key);
            size--;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
    * Returns the value associated with the parameter key.
    * @param key key to lookup in the map
    * @return Value associated with key or null if not found
    */
    @Override
    public V getValue(K key) {
        return findValue(root, key);
    }

    /**
    * Returns the first key found with the parameter value.
    * @param value value to locate
    * @return key of first item found with the matching value
    */
    @Override
    public K getKey(V value) {
        Iterator<K> keys = keys();
        while (keys.hasNext()) {
            K tmp_key = keys.next();
            if (getValue(tmp_key) == value)
                return tmp_key;
        } return null;
    }

    /**
    * Identifies the size of the map.
    * @return Number of entries stored in the map.
    */
    @Override
    public int size() {
        return this.size;
    }

    /**
    * Indicates if the map contains nothing.
    * @return true if the map is empty, as the method cryptically indicates.
    */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
    * Resets the map to an empty state with no entries.
    */
    @Override
    public void clear() {
        this.root = null;
    }

    /**
    * Provides a key iterator.
    * @return Iterator over the keys (some data structures provided sorted)
    */
    @Override
    public Iterator<K> keys() {
        return new KeyIterator(root);
    }

    /**
    * Provides a value iterator. The values arrive corresponding to their
    * keys in the key order.
    * @return Iterator over the values.
    */
    @Override
    public Iterator<V> values() {
        return new ValueIterator();
    }

    private int compare(K x, K y) {
        return x.compareTo(y);
    }

    private Node<K, V> insert(Node<K, V> p, K key, V value) {
        if (p == null)
            return new Node<K, V>(key, value);
        if (compare(key, p.key) == 0) {
            p.value = value;
            return p;
        }
        if (compare(key, p.key) <= 0)
            p.left = insert(p.left, key, value);
        else
            p.right = insert(p.right, key, value);
        return p;
    }

    public V findValue(Node<K, V> p, K key) {
        if (p == null)
            return null;
        else if (compare(key, p.key) == 0)
            return p.value;
        else if (compare(key, p.key) < 0)
            return findValue(p.left, key);
        else
            return findValue(p.right, key);
    }

    private Node<K, V> deleteKey(Node<K, V> p, K toDelete) {
        if (p == null)
            throw new RuntimeException();
        else if (compare(toDelete, p.key) < 0)
            p.left = deleteKey(p.left, toDelete);
        else if (compare(toDelete, p.key) > 0)
            p.right = deleteKey(p.right, toDelete);
        else {
            if (p.left == null)
                return p.right;
            else if (p.right == null)
                return p.left;
            else {
                p.key = getData(p.left);
                p.left = deleteKey(p.left, p.key);
            }
        } return p;
    }

    private K getData(Node<K, V> p) {
        while (p.right != null)
            p = p.right;
        return p.key;
    }

    public class KeyIterator<K> implements Iterator<K> {
        public Stack<Node<Comparable<K>, V>> stack = new Stack<Node<Comparable<K>, V>>();
        
        public KeyIterator(Node<Comparable<K>, V> root) {
            if (root == null)
                return;
            stack.push(root);
            while (root.left != null) {
                stack.push(root.left);
                root = root.left;
            }
        }

        public boolean hasNext() {
            return stack.size() > 0;
        }

        public K next() {
            Node<Comparable<K>, V> node = stack.pop();
            if (node.right != null) {
                Node<Comparable<K>, V> temp = node.right;
                stack.push(temp);
                while (temp.left != null) {
                    stack.push(temp.left);
                    temp = temp.left;
                }
            } return (K) node.key;
        }
    }

    public class ValueIterator implements Iterator<V> {
        Stack<Node<K, V>> stack = new Stack<Node<K, V>>();

        public ValueIterator() {
            if (root != null)
                stack.push(root);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public V next() {
            Node<K, V> current = stack.peek();
            if (current.left != null) {
                stack.push(current.left);
            } else {
                Node<K, V> tmp = stack.pop();
                while (tmp.right == null) {
                    if (stack.isEmpty())
                        return current.value;
                    tmp = stack.pop();
                }
                stack.push(tmp.right);
            } return current.value;
        }
    }
}