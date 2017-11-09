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
public class HeapTest extends TestCase {

    private static final int DEFAULT_TEST_SIZE = 16;
    private static final int VALUE_TARGET = 310;
    private static final int VALUE_NOT_TARGET = 620;

    private Heap<Integer> sut;

    public void setUp() throws Exception {
        sut = new Heap<>();
    }

    public void test_defaultConstructor_naturalOrder() {
        List<Integer> testValues = getInverseValueList(DEFAULT_TEST_SIZE);
        //setup the test by adding several values in inverse
        for (Integer value : testValues) {
            sut.offer(value);
        }        
        assertTrue(sut.containsAll(testValues));
        failIfPollOutOfOrder(DEFAULT_TEST_SIZE);
        assertTrue(sut.isEmpty());
    }

    private static final List<Integer> getInverseValueList(int size) {
        List<Integer> values = new LinkedList<>();
        for (int i = size; i > 0; i--) {
            values.add(values.size(), i);
        }
        return values;
    }

    private void failIfPollOutOfOrder(int testSize) {
        // remove everything from the list and verify it arrives in order
        Integer previous = 0;
        for (int i = 0; i < testSize; i++) {
            Integer current = sut.poll();
            assertTrue(previous <= current);
            previous = current;
        }
    }

    public void test_collectionConstructor_naturalOrder() {

        List<Integer> testValues = getInverseValueList(DEFAULT_TEST_SIZE);
        // add everything to the heap through the collection constructor
        sut = new Heap<>(testValues);
        assertTrue(sut.containsAll(testValues));
        assertThat(sut.size(), is(equalTo(DEFAULT_TEST_SIZE)));
        failIfPollOutOfOrder(DEFAULT_TEST_SIZE);
        assertTrue(sut.isEmpty());
    }

    public void test_comparatorConstructor_newOrder() {

        List<Integer> testValues = getInOrderValueList(DEFAULT_TEST_SIZE);
        sut = new Heap<Integer>(Comparator.reverseOrder());
        // setup the test by adding several values in order
        for (int i = 0; i < DEFAULT_TEST_SIZE; i++)
            sut.offer(testValues.get(i));
        assertTrue(sut.containsAll(testValues));
        failIfPollOutOfInverseOrder(DEFAULT_TEST_SIZE);
    }

    private static final List<Integer> getInOrderValueList(int size) {
        List<Integer> values = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            values.add(values.size(), i);
        }
        return values;
    }

    private void failIfPollOutOfInverseOrder(int testSize) {
        // remove everything from the list and verify it arrives in order
        Integer previous = testSize + 1;
        for (int i = 0; i < testSize; i++) {
            Integer current = sut.poll();
            assertTrue(previous >= current);
            previous = current;
        }
    }

    public void test_comparatorCollectionConstructor_newOrder() {
        sut = new Heap(getInOrderValueList(DEFAULT_TEST_SIZE), Comparator
                .reverseOrder());
        failIfPollOutOfInverseOrder(DEFAULT_TEST_SIZE);
        assertTrue(sut.isEmpty());
        assertNull(sut.poll());
    }

    public void test_offer() {
        List<Integer> testValues = getInverseValueList(DEFAULT_TEST_SIZE);
        for (Integer datum : testValues) {
            assertTrue(sut.offer(datum));
            assertThat(sut.peek(), is(equalTo(datum)));
        }
        assertTrue(sut.containsAll(testValues));
        assertThat(sut.size(), is(equalTo(DEFAULT_TEST_SIZE)));
    }

    public void test_poll() {
        sut = new Heap(getInverseValueList(DEFAULT_TEST_SIZE), Comparator
                .naturalOrder());
        failIfPollOutOfOrder(DEFAULT_TEST_SIZE);
        assertTrue(sut.isEmpty());
        assertNull(sut.poll());
    }

    public void test_peek() {
        assertNull(sut.peek());
        sut.offer(VALUE_TARGET);
        assertThat(sut.peek(), is(equalTo(VALUE_TARGET)));
        sut.offer(VALUE_NOT_TARGET);
        assertThat(sut.peek(), is(equalTo(VALUE_TARGET)));

        sut.clear();
        assertNull(sut.peek());

        sut.offer(VALUE_NOT_TARGET);
        sut.offer(VALUE_TARGET);
        assertThat(sut.peek(), is(equalTo(VALUE_TARGET)));
    }

    /***
     * The sort methods offer static access, so we will not use an instance
     * of a heap during these operations.
     */
    public void test_sort_natural() {
        final List<Integer> underTest = getInverseValueList(DEFAULT_TEST_SIZE);
        Heap.sort(underTest);
        Integer previous = -1;
        for (Integer datum : underTest) {
            assertThat(datum > previous, is(true));
            previous = datum;
        }
        assertThat(underTest.size(), is(equalTo(DEFAULT_TEST_SIZE)));

    }

    // /***
    //  * The sort methods offer static access, so we will not use an instance
    //  * of a heap during these operations.
    //  */
    public void test_sort_comparator() {
        final List<Integer> underTest = getInOrderValueList(DEFAULT_TEST_SIZE);
        Heap.sort(underTest, Comparator.reverseOrder());

        Integer previous = DEFAULT_TEST_SIZE + 1;
        for (Integer datum : underTest) {
            assertThat(datum < previous, is(true));
            previous = datum;
        }

        assertThat(underTest.size(), is(equalTo(DEFAULT_TEST_SIZE)));
    }
}