package genericqueue;

import java.util.NoSuchElementException;

/**
 * An interface representing a generic queue. Provides basic support for queue management such as retrieving its
 * size, returning the elements at the first and last positions, enqueue and dequeue.
 */
public interface Queue<T> extends Iterable<T> {

    /**
     * Returns the size of the queue, which is the amount of elements it currently holds.
     * @return the number of elements in the queue
     */
    int size();

    /**
     * Returns a boolean stating if the queue is empty.
     * @return true if the queue is empty, false if it's not
     */
    boolean isEmpty();

    /**
     * Adds an <code>integer</code> element to the end of the queue.
     * @param element integer to enqueue
     */
    void enqueue(T element);

    /**
     * Removes the first <code>integer</code> element from the queue.
     * @return the first element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    T dequeue() throws NoSuchElementException;

    /**
     * Returns the first <code>integer</code> element currently in the queue.
     * @return the first element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    T first() throws NoSuchElementException;

    /**
     * Returns the last <code>integer</code> element currently in the queue.
     * @return the last element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    T last() throws NoSuchElementException;

    /**
     * Returns a string representation of the current queue content.
     * @return the queue as a String
     */
    String toString();
}
