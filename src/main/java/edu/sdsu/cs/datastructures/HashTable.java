package edu.sdsu.cs.datastructures;
import java.util.*;

/**
* Assignment 4: HashMap and BinarySearchTree
*
* @author Colin Casazza, cssc0236
*/

public final class HashTable<K extends Comparable<K>, V> implements MapADT<K, V> {

    private int initial_capacity = 12;
    private BST<K, V>[] storage;
    private BST<K, V>[] tmp;
    private int size;

    public HashTable() {
        storage = new BST[getPrime(initial_capacity + 2)];
        size = 0;
    }

    public HashTable(int size) {
        storage = new BST[getPrime(size + 2)];
        size = 0;
    }

    public HashTable(MapADT<K, V> inputData) {
        Iterator<K> in_keys = inputData.keys();
        while (in_keys.hasNext()) {
            K current_key = in_keys.next();
            this.add(current_key, inputData.getValue(current_key));
        }
    }

    /**
    * Returns true if the map has an object for the corresponding key.
    * @param key object to search for
    * @return true if within map, false otherwise
    */
    @Override
    public boolean contains(K key) {
        int pos = hashIndex(key);
        if (storage[pos] == null)
            return false;
        else
            return storage[pos].contains(key);
    }

    /**
    * Adds the given key/value pair to the map.
    * @param key Key to add to the map
    * @param value Corresponding value to associate with the key
    * @return the previous value associated with this key or null if new
    */
    @Override
    public V add(K key, V value) {

        if (size / storage.length > 0.75)
            increaseStorageSize();

        V ret = null;
        int pos = hashIndex(key);
        BST<K, V> root = null;
        if (storage[pos] == null) {
            root = new BST<>();
            root.add(key, value);
            storage[pos] = root;
            size++;
            return null;
        } else {
            root = storage[pos];
            ret = root.getValue(key);
            if (root.contains(key) != true)
                size++;
            root.add(key, value);
            return ret;
        }
    }

    /**
    * Removes the key/value pair identified by the key parameter from the map.
    * @param key item to remove
    * @return true if removed, false if not found or unable to remove
    */
    @Override
    public boolean delete(K key) {
        if (size / storage.length < 0.75)
            decreaseStorageSize();
        int pos = hashIndex(key);
        try {
            BST<K, V> tree = storage[pos];
            if (tree.contains(key)) {
                tree.delete(key);
                return true;
            }
        } catch (Exception e) {
            return false;
        } return false;
    }

    /**
    * Returns the value associated with the parameter key.
    * @param key key to lookup in the map
    * @return Value associated with key or null if not found
    */
    @Override
    public V getValue(K key) {
        try {
            return storage[hashIndex(key)].getValue(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
    * Returns the first key found with the parameter value.
    * @param value value to locate
    * @return key of first item found with the matching value
    */
    @Override
    public K getKey(V value) {
        Iterator<K> keys = new KeyIterator<>();
        while (keys.hasNext()) {
            K key = keys.next();
            if (getValue(key).equals(value))
                return key;
        } return null;
    }

    /**
    * Identifies the size of the map.
    * @return Number of entries stored in the map.
    */
    @Override
    public int size() {
        return size;
    }

    /**
    * Indicates if the map contains nothing.
    * @return true if the map is empty, as the method cryptically indicates.
    */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
    * Resets the map to an empty state with no entries.
    */
    @Override
    public void clear() {
        for (BST<K, V> tree : storage)
            tree.clear();
    }

    /**
    * Provides a key iterator.
    * @return Iterator over the keys (some data structures provided sorted)
    */
    @Override
    public Iterator<K> keys() {
        Iterator<K> it = new KeyIterator<>();
        return it;
    }

    /**
    * Provides a value iterator. The values arrive corresponding to their
    * keys in the key order.
    * @return Iterator over the values.
    */
    @Override
    public Iterator<V> values() {
        Stack<K> k = (Stack<K>) KeysToStack();
        Stack<V> v = new Stack<V>();
        for (K e : k) {
            v.push(getValue(e));
        } return v.iterator();
    }

    /**
     * prime number evaluation branched from 
     * http://www.sanfoundry.com/java-program-implement-hash-tables-chaining-binary-trees/ 
     */
    private int getPrime(int n) {
        int next_prime = n;
        boolean found = false;
        while (!found) {
            next_prime++;
            if (isPrime(next_prime)) {
                found = true;
            }
        } return next_prime;
    }
    private boolean isPrime(int n) {
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        } return true;
    }

    private int hashIndex(K key) {
        int hash_val = key.hashCode();
        hash_val %= storage.length;
        if (hash_val < 0)
            hash_val += storage.length;
        return hash_val;
    }

    public void increaseStorageSize() {
        Stack<BST> trees = new Stack<BST>();
        for(int i = 0; i < storage.length; i++){
            try {
                trees.push(storage[i]);
            } catch (Exception e) { }
        }
        storage = new BST[storage.length * 2];
        while(!trees.isEmpty()){
            try {
                BST<K,V> current_tree = trees.pop();
                Iterator<K> keys = current_tree.keys();
                while(keys.hasNext()){
                    K key = keys.next();
                    this.add(key, current_tree.getValue(key));
                } 
            } catch (Exception e) { }           
        }
    }

    private void decreaseStorageSize() {
        tmp = Arrays.copyOf(storage, getPrime((int) Math.floor(storage.length / 1.5)));
        Iterator<K> keys = keys();
        while (keys.hasNext()) {
            K key = keys.next();
            int pos = hashIndex(key);
            BST<K, V> root = null;
            if (tmp[pos] == null) {
                root = new BST<>();
                root.add(key, getValue(key));
                tmp[pos] = root;
            }
        }
        storage = tmp;
    }

    private Stack<K> KeysToStack() {
        Stack<K> s = new Stack<K>();
        for (int i = 0; i < storage.length; i++) {
            try {
                BST<K, V> tree = storage[i];
                Iterator<K> keys = tree.keys();
                while (keys.hasNext()) {
                    s.push(keys.next());
                }
            } catch (Exception e) {
            }
        }
        sortStack(s);
        return s;
    }

    //sortStack() and sortedInsert() branched from http://www.geeksforgeeks.org/sort-a-stack-using-recursion/
    private void sortedInsert(Stack<K> s, K k) {
        if (s.isEmpty() || (k.compareTo(s.peek()) > 0)) {
            s.push(k);
            return;
        }
        K temp = s.pop();
        sortedInsert(s, k);
        s.push(temp);
    }
    private void sortStack(Stack<K> s) {
        if (!s.isEmpty()) {
            K x = s.pop();
            sortStack(s);
            sortedInsert(s, x);
        }
    }

    private class KeyIterator<K> implements Iterator<K> {
        private Stack<K> keys;
        private int size;

        public KeyIterator() {
            keys = (Stack<K>) KeysToStack();
            size = keys.size();
        }

        public boolean hasNext() {
            return keys.isEmpty() == false;
        }

        public K next() {
            if (this.hasNext()) {
                size--;
                return keys.pop();
            }
            return null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
