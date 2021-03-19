package genericqueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class representing a linked list implementation of a generic queue. Provides support for queue management such as
 * retrieving its size, returning the elements at the first and last positions, enqueue and dequeue as well as an
 * iterator for easier queue traversal.
 */
public class LinkedQueue<T> implements Queue<T> {
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
     * Adds an element to the end of the queue.
     *
     * @param element element to enqueue
     */
    @Override
    public void enqueue(T element) {
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
    public T dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }

        T value = head.value;
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
    public T first() throws NoSuchElementException {
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
    public T last() throws NoSuchElementException {
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
    public Iterator<T> iterator() {
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
        Iterator<T> it = iterator();

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
        private T value;
        Node next = null;

        /**
         * Constructor.
         * @param value the value to store
         */
        private Node(T value) {
            this.value = value;
        }
    }

    /**
     * Private class providing an iterator to use for easier traversal of the linked generic queue.
     * Contains basic support for iterating over the Node objects along with a method to check whether or not there
     * are more elements in the queue (next and hasNext). The remove-method has not been implemented.
     */
    private class QueueIterator implements Iterator<T> {
        private Node node = head;

        /**
         * Returns the value of the next Node in the linked generic queue.
         * @throws NoSuchElementException if next is null
         * @return the value contained in the next Node object
         */
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T value = node.value;
            node = node.next;

            return value;
        }

        /**
         * Checks if there are any more Nodes in our queue.
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
