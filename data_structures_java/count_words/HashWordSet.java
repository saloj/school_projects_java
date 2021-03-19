package count_words;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class representing a HashSet for Word-objects.
 */
public class HashWordSet implements WordSet {
    private int size = 0;
    private Node[] buckets = new Node[8];

    /**
     * Adds the Word-object to our HashWordSet IF no duplicates exist.
     * @param word - the Word-object passed in as argument
     */
    @Override
    public void add(Word word) {
        // most of the add method was covered in the latest lecture
        int position = getBucket(word);
        Node node = buckets[position];

        while (node != null) {
            // if duplicate => don't add
            if (node.value.equals(word)) {
                return;
            } else {
                // go to next node in our bucket
                node = node.next;
            }
        }

        // no duplicates? add the element
        node = new Node(word);
        node.next = buckets[position];
        buckets[position] = node;

        size++;

        // if we've run out of space, rehash and increase bucket size
        if (size == buckets.length) {
            rehash();
        }
    }

    /**
     * Checks whether the passed in Word-object already exists in the set or not.
     * @param word - the Word-object passed in as argument
     */
    @Override
    public boolean contains(Word word) {
        // most of the contains method was covered in the latest lecture
        int position = getBucket(word);
        Node node = buckets[position];

        // search the node list for the provided Word element if there's already elements occupying the bucket at the
        // computed location
        while (node != null) {
            if (node.value.equals(word)) {
                // found
                return true;
            } else {
                // check next
                node = node.next;
            }
        }
        // not found
        return false;
    }

    /**
     * The size of the HashWordSet.
     * @return - the current size of the set (number of elements inserted)
     */
    @Override
    public int size() {
        return size;
    }

    // helper method to compute the index for the Word-object
    private int getBucket(Word word) {
        // remove negative value if such is returned from hashCode
        int hash = Math.abs(word.hashCode());
        return hash % buckets.length;
    }

    // helper method for rehashing if we've run out of space
    private void rehash() {
        // copy the current buckets
        Node[] tempArray = new Node[buckets.length];
        System.arraycopy(buckets, 0, tempArray, 0, buckets.length);

        // increase the size of the new empty buckets
        buckets = new Node[2 * tempArray.length];
        size = 0;

        // insert the previous values into the new buckets
        for (Node node : tempArray) {
            if (node == null) {
                continue;
            }
            while (node != null) {
                add(node.value);
                node = node.next;
            }
        }
    }

    @Override
    public Iterator<Word> iterator() {
        return new HashIterator();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("[ ");

        for (int i = 0; i < buckets.length; i++) {
            Node node = buckets[i];

            while (node != null) {
                text.append(node.value);
                text.append(" ");

                node = node.next;
            }
        }

        return text.toString() + " ]";
    }

    // private inner class representing our "buckets"
    private class Node {
        Word value;
        Node next = null;

        public Node() {
        }

        public Node(Word word) {
            value = word;
        }

        public String toString() {
            return value.toString();
        }
    }

    private class HashIterator implements Iterator<Word> {
         int pos = -1;
         Node node = null;

         // inspiration for "next()" method from:  http://robertovormittag.net/how-to-implement-a-simple-hashset-in-java/
         @Override
         public Word next() throws NoSuchElementException {
            if (node == null || node.next == null) {
                // check next position
                pos++;

                // keep looking for a bucket with nodes
                while (pos < buckets.length && buckets[pos] == null) {
                    pos++;
                }

                // still in bounds?
                if (pos < buckets.length) {
                    node = buckets[pos];
                }
                else {
                    throw new NoSuchElementException();
                }
            }
            // keep going to the next node
            else {
                node = node.next;
            }

            return node.value;
         }

        @Override
        public boolean hasNext() {
            if (node != null && node.next != null) {
                return true;
            }

            // check all nodes
            for (int i = pos + 1; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Not implemented.
         */
        @Override
        public void remove() {
            throw new RuntimeException("remove() is not implemented");
        }
    }
}


