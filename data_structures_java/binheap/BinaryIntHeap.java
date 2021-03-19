package binheap;

/**
 * Class representing a Binary Heap with Integers.
 * Resources used for heapifyAbove & heapifyBelow:
 * Course literature (chapter 23.6), the lecture & Tim Buchalkas DS & A course on Udemy.
 */
public class BinaryIntHeap {
    private int[] heap;
    private int size;

    public BinaryIntHeap() {
        heap = new int[8];
        size = 0;
    }

    /**
     * Adds the integer passed in as argument to the heap.
     * @param n - integer to add
     */
    public void insert(int n) {
        if (isFull()) {
            resize();
        }

        heap[size] = n;

        heapifyAbove(size);
        size++;
    }

    // method to heapify after insertion (if inserted node is greater than its parent => swap places) percolation up
    private void heapifyAbove(int index) {
        int newValue = heap[index];

        // while we aren't at the root and the inserted value is larger than its parent
        while (index > 0 && newValue > heap[getParentIndex(index)]) {
            // percolate the parent down
            heap[index] = heap[getParentIndex(index)];
            // get the index of next node to check
            index = getParentIndex(index);
        }

        // insert the added value after we've traversed the heap all the way to the right spot
        heap[index] = newValue;
    }

    /**
     * Returns and removes the element with the highest priority.
     * @return - the greatest integer
     */
    public int pullHighest() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("The heap is empty");
        }

        // the highest value is always at the root (index 0)
        int pulledValue = heap[0];

        // replace the root with the rightmost value in the heap
        heap[0] = heap[size - 1];
        heapifyBelow(0);

        size--;
        return pulledValue;
    }

    // method to heapify after deletion/pullHighest, percolate down
    private void heapifyBelow(int index) {
        int childToSwap;

        // size - 1 is the last node in the heap
        while (index <= size - 1) {
            int leftChild = getLeftChildIndex(index);
            int rightChild = getRightChildIndex(index);

            // the node (index) has a left child. heaps either have 2 children or only left child
            if (leftChild <= size - 1) {
                // an index that is out of bounds of the heap size => swap with leftChild!
                if (rightChild > size - 1) {
                    childToSwap = leftChild;
                }
                // right child also exists, compare values and swap with the greatest/largest one
                else {
                    childToSwap = (heap[leftChild] > heap[rightChild] ? leftChild : rightChild);
                }

                // current node smaller than its child? swap!
                if (heap[index] < heap[childToSwap]) {
                    int temp = heap[index];

                    heap[index] = heap[childToSwap];
                    heap[childToSwap] = temp;
                }
                else {
                    break;
                }
                // compare the replacement value with its new children
                index = childToSwap;
            }
            // if the node doesn't have any children => done! (node is a leaf)
            else {
                break;
            }
        }
    }

    /**
     * Returns the current size of the heap.
     * @return - the size as an integer
     */
    public int size() {
        return size;
    }

    /**
     * Returns a boolean stating if the heap is empty.
     * @return - true or false depending on if the heap is empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    // return the index of the nodes parent
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    // return the index of the nodes left child
    private int getLeftChildIndex(int index) {
        return (index * 2) + 1;
    }

    // return the index of the nodes right child
    private int getRightChildIndex(int index) {
        return (index * 2) + 2;
    }

    // check if we need to resize
    private boolean isFull() {
        return size == heap.length;
    }

    // resize the heap
    private void resize() {
        int[] temp = new int[2 * heap.length];
        System.arraycopy(heap, 0, temp, 0, heap.length);

        heap = temp;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("[ ");

        for (int i = 0; i < size; i++) {
            text.append(heap[i] + " ");
        }
        text.append("]");

        return text.toString();
    }
}
