package linkedqueuetest;

import fifo.IntQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class representing a linked list implementation of an integer queue. Provides support for queue management such as
 * retrieving its size, returning the elements at the first and last positions, enqueue and dequeue as well as an
 * iterator for easier queue traversal.
 * <p>
 *
 * @author Joel Salo
 * @see Iterator
 * @since 2020-01-29
 */
public class LinkedQueue implements IntQueue {
    private int size = 0;
    private Node head = null;
    private Node tail = null;

    /**
     * Returns the size of the queue, which is the amount of elements it currently holds.
     *
     * @return the number of elements in the queue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a boolean stating if the queue is empty.
     *
     * @return true if the queue is empty, false if it's not
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds an <code>integer</code> element to the end of the queue.
     *
     * @param element integer to enqueue
     */
    @Override
    public void enqueue(int element) {
        if (head == null) {
            head = new Node(element);
            tail = head;
        }
        else {
            tail.next = new Node(element);
            tail = tail.next;
        }

        size++;
    }

    /**
     * Removes the first <code>integer</code> element from the queue.
     *
     * @return the first element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public int dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }

        int value = head.value;
        head = head.next;

        size--;
        return value;
    }

    /**
     * Returns the first <code>integer</code> element currently in the queue.
     *
     * @return the first element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public int first() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }

        return head.value;
    }

    /**
     * Returns the last <code>integer</code> element currently in the queue.
     *
     * @return the last element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public int last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }

        return tail.value;
    }

    /**
     * Returns an <code>integer</code> iterator to enable easier queue traversal.
     *
     * @return an iterator for the queue
     */
    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator();
    }

    /**
     * Returns a string representation of the current queue content.
     *
     * @return the queue as a String
     */
    @Override
    public String toString() {
        StringBuffer temp = new StringBuffer();
        Iterator<Integer> it = iterator();

        temp.append("[");

        while (it.hasNext()) {
            temp.append(" " + it.next());
        }

        temp.append(" ]");
        return temp.toString().trim();
    }

    /**
     * Private class representing a Node object to use for storing the elements of the linked integer queue.
     * Contains the value of the stored element along with a reference to the next object in the linked queue.
     * <p/>
     *
     * @author Joel Salo
     * @since 2020-01-29
     */
    private class Node {
        private int value;
        Node next = null;

        /**
         * Constructor.
         * @param value the <code>integer</code> value to store
         */
        private Node(int value) {
            this.value = value;
        }
    }

    /**
     * Private class providing an iterator to use for easier traversal of the linked integer queue.
     * Contains basic support for iterating over the Node objects along with a method to check whether or not there
     * are more elements in the queue (next and hasNext). The remove-method has not been implemented.
     * <p/>
     *
     * @author Joel Salo
     * @since 2020-01-29
     */
    private class QueueIterator implements Iterator<Integer> {
        private LinkedQueue.Node node = head;

        /**
         * Returns the value of the next Node in the linked integer queue.
         *
         * @throws NoSuchElementException if next is null
         * @return the <code>integer</code> contained in the next Node object
         */
        public Integer next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int value = node.value;
            node = node.next;

            return value;
        }

        /**
         * Checks if there are any more Nodes in our queue.
         *
         * @return true if there are more Nodes, false if there isn't
         */
        public boolean hasNext() {
            return node != null;
        }

        /**
         * Not implemented.
         */
        public void remove() {
            throw new RuntimeException("remove() is not implemented");
        }
    }
}
