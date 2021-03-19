package linkedqueuetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LinkedQueueTest {

    LinkedQueue queue;
    Random rnd = new Random();
    private static int count = 0;

    // Private helper method to randomize the number of elements in the queue (Integer)
    private void buildInteger(LinkedQueue queue, int min, int max) {
        int numberOfElements = rnd.nextInt(max - min + 1) + min;

        for (int i = 0; i < numberOfElements; i++) {
            queue.enqueue(i);
        }
    }

    // Private generic helper method for dequeuing all except the element used to assert functionality, which is
    // dequeued on return
    private static int dequeueHelper(LinkedQueue queue, int lastToPop) {
        while (queue.size() > 0) {
            queue.dequeue();
        }
        queue.enqueue(lastToPop);

        return queue.dequeue();
    }

    @BeforeEach
    void setUp() {
        queue = new LinkedQueue();
        System.out.println("---------- Running test " + (++count) + "/" + "13 ----------");
    }

    @AfterEach
    void tearDown() {
        queue = null;
        System.out.println("---------- Done with test " + count + "  ----------");
    }

    @Test
    void queueSizeZero() {
        assertEquals(0, queue.size(), "Should return size 0");
    }

    @Test
    void queueSizeGreaterThanZero() {
        // singleton
        queue.enqueue(1);
        assertEquals(1, queue.size(), "Should return size 1");
    }

    @Test
    void isEmptyTrue() {
        assertTrue(queue.isEmpty(), "Should return true");
    }

    @Test
    void isEmptyFalse() {
        queue.enqueue(42);
        assertFalse(queue.isEmpty(), "Should return false");
    }

    @Test
    void enqueue() {
        queue.enqueue(1);
        assertNotNull(queue, "Should not be null after an element has been enqueued");
    }

    @Test
    void dequeueEmpty() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue(),
                "Should throw NoSuchElementException");
    }

    @Test
    void dequeueNotEmpty() {
        // basic dequeue with a single element (Integer)
        queue.enqueue(1);
        assertEquals(1, queue.dequeue(), "Should return 1");

        // randomized dequeue with a larger random sample size (Integer)
        int verify = rnd.nextInt(10000 - 1000 + 1000) + 1000;
        buildInteger(queue, verify, verify * 10);
        assertEquals(verify, dequeueHelper(queue, verify), "Should return " + verify);
    }

    @Test
    void firstEmpty() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue(),
                "Should throw NoSuchElementException");
    }

    @Test
    void firstNotEmpty() {
        // singleton (Integer)
        queue.enqueue(1);
        assertEquals(1, queue.first(), "Should return 1");
        queue.dequeue(); // emptying queue for the next assertion to be accurate

        // small sample size (Integer)
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        assertEquals(2, queue.first(), "Should return 2");
    }

    @Test
    void lastEmpty() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue(),
                "Should throw NoSuchElementException");
    }

    @Test
    void lastNotEmpty() {
        // singleton (Integer)
        queue.enqueue(1);
        assertEquals(1, queue.last(), "Should return 1");
        queue.dequeue(); // emptying queue for the next assertion to be accurate

        // small sample size (Integer)
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        assertEquals(4, queue.last(), "Should return 4");
    }

    @Test
    void iteratorNextException() {
        Iterator<Integer> stringIt = queue.iterator();

        assertThrows(NoSuchElementException.class, stringIt::next,
                "Should throw NoSuchElementException");
    }

    @Test
    void queueToString() {
        String expectedInt = "[ 1 2 3 ]";
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(expectedInt, queue.toString(), "Should return [ 1 2 3 ]");
    }
}