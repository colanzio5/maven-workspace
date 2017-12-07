package edu.sdsu.cs.datastructures;

import junit.framework.TestCase;

import edu.sdsu.cs.datastructures.BinarySearchTree;
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

    private static final int DEFAULT_TEST_SIZE = 10;
    private static final int VALUE_TARGET = 310;
    private static final int VALUE_NOT_TARGET = 620;
    private HashTable<String, Integer> ntable;
    private BinarySearchTree<String, Integer> tree;

    public void setUp() throws Exception {
        System.out.println("Test - Setup Ran");
    }

    public void test_defaultConstructor_naturalOrder() {
        System.out.println("Test - Unit Test Ran");
        ntable = new HashTable<>();
        List<Integer> testValues = getInverseValueList(DEFAULT_TEST_SIZE);
        
              //setup the test by adding several values in inverse
        for (Integer value : testValues) {
            ntable.add(value.toString(),1);
        }
         


        Iterator<String> it2 = ntable.keys();
        while(it2.hasNext()){
            String k = it2.next();
            System.out.println("K: " + k + " V: "  + ntable.getValue(k));
        }  
    }
 

    private static final List<Integer> getInverseValueList(int size) {
        List<Integer> values = new LinkedList<>();
        for (int i = size; i > 0; i--) {
            values.add(values.size(), i);
        }
        return values;
    }
}