package genericqueue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    LinkedQueue<Integer> intQueue;
    LinkedQueue<String> stringQueue;
    Random rnd = new Random();
    private static int count = 0;

    // Private helper method to randomize the number of elements in the queue (Integer)
    private void buildInteger(LinkedQueue<Integer> queue, int min, int max) {
        int numberOfElements = rnd.nextInt(max - min + 1) + min;

        for (int i = 0; i < numberOfElements; i++) {
            queue.enqueue(i);
        }
    }

    // Private helper method to randomize the number of elements in the queue (String)
    private void buildString(LinkedQueue<String> queue, int min, int max) {
        int numberOfElements = rnd.nextInt(max - min + 1) + min;

        for (int i = 0; i < numberOfElements; i++) {
            queue.enqueue(i + "");
        }
    }

    // Private generic helper method for dequeuing all except the element used to assert functionality, which is
    // dequeued on return
    private static <T> T dequeueGeneric(LinkedQueue<T> queue, T lastToPop) {
        while (queue.size() > 0) {
            queue.dequeue();
        }
        queue.enqueue(lastToPop);

        return queue.dequeue();
    }

    @BeforeEach
    void setUp() {
        intQueue = new LinkedQueue<>();
        stringQueue = new LinkedQueue<>();
        System.out.println("---------- Running test " + (++count) + "/" + "13 ----------");
    }

    @AfterEach
    void tearDown() {
        intQueue = null;
        stringQueue = null;
        System.out.println("---------- Done with test " + count + "  ----------");
    }

    @Test
    void queueSizeZero() {
        assertEquals(0, intQueue.size(), "Should return size 0");
        assertEquals(0, stringQueue.size(), "Should return size 0");
    }

    @Test
    void queueSizeGreaterThanZero() {
        // singleton
        intQueue.enqueue(1);
        assertEquals(1, intQueue.size(), "Should return size 1");

        // singleton
        stringQueue.enqueue("Not zero test");
        assertEquals(1, stringQueue.size(), "Should return size 1");
    }

    @Test
    void isEmptyTrue() {
        assertTrue(intQueue.isEmpty(), "Should return true");
        assertTrue(stringQueue.isEmpty(), "Should return true");
    }

    @Test
    void isEmptyFalse() {
        intQueue.enqueue(42);
        assertFalse(intQueue.isEmpty(), "Should return false");

        stringQueue.enqueue("Not empty test");
        assertFalse(stringQueue.isEmpty(), "Should return false");
    }

    @Test
    void enqueue() {
        intQueue.enqueue(1);
        assertNotNull(intQueue, "Should not be null after an element has been enqueued");

        stringQueue.enqueue("Enqueue");
        assertNotNull(stringQueue, "Should not be null after an element has been enqueued");
    }

    @Test
    void dequeueEmpty() {
        assertThrows(NoSuchElementException.class, () -> intQueue.dequeue(),
                "Should throw NoSuchElementException");

        assertThrows(NoSuchElementException.class, () -> stringQueue.dequeue(),
                "Should throw NoSuchElementException");
    }

    @Test
    void dequeueNotEmpty() {
        // basic dequeue with a single element (Integer)
        intQueue.enqueue(1);
        assertEquals(1, intQueue.dequeue(), "Should return 1");

        // randomized dequeue with a larger random sample size (Integer)
        int verify = rnd.nextInt(10000 - 1000 + 1000) + 1000;
        buildInteger(intQueue, verify, verify * 10);
        assertEquals(verify, dequeueGeneric(intQueue, verify), "Should return " + verify);

        // basic dequeue with a single element (String)
        stringQueue.enqueue("Dequeue");
        assertEquals("Dequeue", stringQueue.dequeue(), "Should return the string " + "Dequeue");

        // randomized dequeue with a larger random sample size (String)
        buildString(stringQueue, verify, verify * 10);
        String verify2 = Integer.toString(verify);
        assertEquals(verify2, dequeueGeneric(stringQueue, verify2), "Should return the string " +
                "\"" + verify2 + "\"");
    }

    @Test
    void firstEmpty() {
        assertThrows(NoSuchElementException.class, () -> intQueue.dequeue(),
                "Should throw NoSuchElementException");

        assertThrows(NoSuchElementException.class, () -> stringQueue.dequeue(),
                "Should throw NoSuchElementException");
    }

    @Test
    void firstNotEmpty() {
        // singleton (Integer)
        intQueue.enqueue(1);
        assertEquals(1, intQueue.first(), "Should return 1");
        intQueue.dequeue(); // emptying queue for the next assertion to be accurate

        // small sample size (Integer)
        intQueue.enqueue(2);
        intQueue.enqueue(3);
        intQueue.enqueue(4);
        assertEquals(2, intQueue.first(), "Should return 2");

        // singleton (String)
        stringQueue.enqueue("First");
        assertEquals("First", stringQueue.first(), "Should return the string " + "\"" +  "First" +
                "\"");
        stringQueue.dequeue(); // emptying queue for the next assertion to be accurate

        // small sample size (String)
        stringQueue.enqueue("Second");
        stringQueue.enqueue("Third");
        stringQueue.enqueue("Fourth");
        assertEquals("Second", stringQueue.first(), "Should return the string " + "\"" + "Second" +
                "\"");
    }

    @Test
    void lastEmpty() {
        assertThrows(NoSuchElementException.class, () -> intQueue.dequeue(),
                "Should throw NoSuchElementException");

        assertThrows(NoSuchElementException.class, () -> stringQueue.dequeue(),
                "Should throw NoSuchElementException");
    }

    @Test
    void lastNotEmpty() {
        // singleton (Integer)
        intQueue.enqueue(1);
        assertEquals(1, intQueue.last(), "Should return 1");
        intQueue.dequeue(); // emptying queue for the next assertion to be accurate

        // small sample size (Integer)
        intQueue.enqueue(2);
        intQueue.enqueue(3);
        intQueue.enqueue(4);
        assertEquals(4, intQueue.last(), "Should return 4");

        // singleton (String)
        stringQueue.enqueue("First");
        assertEquals("First", stringQueue.last(), "Should return the string " + "\"" +  "First" +
                "\"");
        stringQueue.dequeue(); // emptying queue for the next assertion to be accurate

        // small sample size (String)
        stringQueue.enqueue("Second");
        stringQueue.enqueue("Third");
        stringQueue.enqueue("Fourth");
        assertEquals("Fourth", stringQueue.last(), "Should return the string " + "\"" +  "Fourth" +
                "\"");
    }

    @Test
    void iteratorNextException() {
        Iterator<Integer> intIt = intQueue.iterator();
        assertThrows(NoSuchElementException.class, intIt::next,
                "Should throw NoSuchElementException");

        Iterator<String> stringIt = stringQueue.iterator();
        assertThrows(NoSuchElementException.class, stringIt::next,
                "Should throw NoSuchElementException");
    }

    @Test
    void queueToString() {
        String expectedInt = "[ 1 2 3 ]";
        intQueue.enqueue(1);
        intQueue.enqueue(2);
        intQueue.enqueue(3);
        assertEquals(expectedInt, intQueue.toString(), "Should return [ 1 2 3 ]");

        String expectedString = "[ One nice test ]";
        stringQueue.enqueue("One");
        stringQueue.enqueue("nice");
        stringQueue.enqueue("test");
        assertEquals(expectedString, stringQueue.toString(), "Should return [ One nice test ]");
    }
}