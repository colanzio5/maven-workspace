package edu.sdsu.cs.datastructures;

import junit.framework.TestCase;

import java.util.*;

import javax.swing.text.StyleConstants.FontConstants;
import javax.xml.ws.Dispatch;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by shawn on 10/21/17.
 */
public class HashTest extends TestCase {

    private static final int DEFAULT_TEST_SIZE = 8;
    private static final int VALUE_TARGET = 310;
    private static final int VALUE_NOT_TARGET = 620;
    private HashTable<String, Integer> ntable;
    private BST<String,Integer> tree;
    public void setUp() throws Exception {
        System.out.println("Test - Setup Ran");
    }

    public void test_defaultConstructor_naturalOrder() {
        System.out.println("Test - Unit Test Ran");
        ntable = new HashTable<>();
        ntable.add("aaa", 1);
        ntable.add("bbb", 1);
        ntable.add("ccc", 1);
        Iterator<String> it = ntable.keys();
        while(it.hasNext()){
            String k = it.next();
            System.out.println("K: " + k + " V: "  + ntable.getValue(k));
        }
        ntable.increaseStorageSize();
        ntable.add("a1aa", 1);
        ntable.add("b2bb", 1);
        ntable.add("cc2c", 1);
        Iterator<String> it2 = ntable.keys();
        while(it2.hasNext()){
            String k = it2.next();
            System.out.println("K: " + k + " V: "  + ntable.getValue(k));
        }
    }
}