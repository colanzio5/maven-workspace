package edu.sdsu.cs.datastructures;
import java.util.Iterator;
import javax.net.ssl.ExtendedSSLSession;
import javax.sound.midi.MidiChannel;

public final class Hash<K extends Comparable<K>, V> implements MapADT<K, V> {
    /**
    * Returns true if the map has an object for the corresponding key.
    * @param key object to search for
    * @return true if within map, false otherwise
    */
    @Override
    public boolean contains(K key){
        return false;
    }

    /**
    * Adds the given key/value pair to the map.
    * @param key Key to add to the map
    * @param value Corresponding value to associate with the key
    * @return the previous value associated with this key or null if new
    */

    @Override
    public V add(K key, V value){
        return null;
    }

    /**
    * Removes the key/value pair identified by the key parameter from the map.
    * @param key item to remove
    * @return true if removed, false if not found or unable to remove
    */
    @Override
    public boolean delete(K key){
        return false;
    }

    /**
    * Returns the value associated with the parameter key.
    * @param key key to lookup in the map
    * @return Value associated with key or null if not found
    */
    @Override
    public V getValue(K key){
        return null;
    }

    /**
    * Returns the first key found with the parameter value.
    * @param value value to locate
    * @return key of first item found with the matching value
    */
    @Override
    public K getKey(V value){
        return null;
    }

    /**
    * Identifies the size of the map.
    * @return Number of entries stored in the map.
    */
    @Override
    public int size(){
        return 0;
    }

    /**
    * Indicates if the map contains nothing.
    * @return true if the map is empty, as the method cryptically indicates.
    */
    @Override
    public boolean isEmpty(){
        return false;
    }

    /**
    * Resets the map to an empty state with no entries.
    */
    @Override
    public void clear(){
    }

    /**
    * Provides a key iterator.
    * @return Iterator over the keys (some data structures provided sorted)
    */
    @Override
    public Iterator<K> keys(){
        return null;
    }

    /**
    * Provides a value iterator. The values arrive corresponding to their
    * keys in the key order.
    * @return Iterator over the values.
    */
    @Override
    public Iterator<V> values(){
        return null;
    }
}