package edu.sdsu.cs.datastructures;
/**
* Assignment 4: HashMap and BinarySearchTree
*
* @author Colin Casazza, cssc0236
*/
public class Node<K extends Comparable, V> {
    public K key;
    public V value;
    public Node<K, V> left, right;

    public Node(K key, V value, Node<K, V> l, Node<K, V> r) {
        left = l;
        right = r;
        this.key = key;
        this.value = value;
    }

    public Node(K key, V value) {
        this(key, value, null, null);
    }

    public String toString() {
        return key.toString();
    }
}