package edu.sdsu.cs.datastructures;

import junit.framework.TestCase;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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

    private Heap<Integer> sut;

    public void setUp() throws Exception {
        System.out.println("Test - Setup Ran");
    }

    public void test_defaultConstructor_naturalOrder() {
        System.out.println("Test - Unit Test Ran");
    }
}