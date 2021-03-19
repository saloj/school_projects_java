package binheap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BinaryIntHeapTest {
    BinaryIntHeap heap;
    private static int count = 0;

    // helper method to build a random heap and return the max value
    private int buildRandomHeap(BinaryIntHeap heap, int size, int maxValue) {
        Random rnd = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = rnd.nextInt(maxValue) + 1;
        }

        int max = Arrays.stream(array).max().getAsInt();

        for (int num : array) {
            heap.insert(num);
        }

        return max;
    }

    @BeforeEach
    void setUp() {
        heap = new BinaryIntHeap();
        System.out.println("---------- Running test " + (++count) + "/" + "8 ----------");
    }

    @AfterEach
    void tearDown() {
        heap = null;
        System.out.println("---------- Done with test " + count + "  ----------");
    }

    @Test
    void insert() {
        heap.insert(80);
        heap.insert(75);
        heap.insert(60);

        int expected = 3;
        int actual = heap.size();

        assertEquals(expected, actual, "should return " + expected + " as heap.size()");
        assertFalse(heap.isEmpty(), "should be false if the elements have been inserted correctly");
    }

    @Test
    void pullHighestKnown() {
        heap.insert(80);
        heap.insert(75);
        heap.insert(60);
        heap.insert(68);
        heap.insert(100);

        int expected = 100;
        int actual = heap.pullHighest();

        assertEquals(expected, actual, "should return " + expected + " as the highest element");
    }

    @Test
    void pullHighestUnknown() {
       int expected = buildRandomHeap(heap, 128, 512);
       int actual = heap.pullHighest();

       assertEquals(expected, actual, expected + " and " + actual + " should match");
    }

    @Test
    void sizeZero() {
        assertEquals(0, heap.size(), "heap should be empty, thus size should be 0");
    }

    @Test
    void sizeNotZero() {
        heap.insert(80);
        heap.insert(60);

        int expected = 2;

        assertEquals(expected, heap.size(), "heap size should be " + expected);
    }

    @Test
    void isEmptyTrue() {
        assertTrue(heap.isEmpty(), "no elements added, heap should be empty");
    }

    @Test
    void isEmptyFalse() {
        heap.insert(10);

        assertFalse(heap.isEmpty(), "1 element added, should not be empty");
    }

    @Test
    void testToString() {
        heap.insert(80);
        heap.insert(75);
        heap.insert(60);
        heap.insert(68);

        String expected = "[ 80 75 60 68 ]";
        String actual = heap.toString();

        assertEquals(expected, actual, expected + " and " + actual + " should match");
    }
}