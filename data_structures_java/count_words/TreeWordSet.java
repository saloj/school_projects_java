package count_words;

import java.util.Iterator;

/**
 * Class representing a TreeSet for Word-objects.
 */
public class TreeWordSet implements WordSet {
    private BinarySearchTreeSet root = null;
    private int size = 0;

    /**
     * Adds the Word-object to the tree as long as it's not a duplicate.
     * @param word - the Word-object
     */
    @Override
    public void add(Word word) {
        if (root == null) {
            root = new BinarySearchTreeSet(word);
            size++;
        }
        else {
            root.add(word);
        }
    }

    /**
     * Checks whether the passed in Word-object exists in the tree or not.
     * @param word - the Word-object
     * @return - true/false depending on if it exists or not
     */
    @Override
    public boolean contains(Word word) {
        if (root == null) {
            return false;
        }
        else {
            return root.contains(word);
        }
    }

    /**
     * The size of the set.
     * @return - the size of the set
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Word> iterator() {
        return new TreeIterator();
    }

    /**
     * Prints the current contents of the TreeWordSet.
     * @return - Word-objects in String format
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("[ ");

        if (root != null) {
            Iterator<Word> it = iterator();
            while (it.hasNext()) {
                text.append(it.next().toString() + " ");
            }
        }

        return text.toString() + " ]";
    }

    // private inner class to handle the BinarySearchTreeSet
    private class BinarySearchTreeSet {
        protected Word word;
        BinarySearchTreeSet left = null;
        BinarySearchTreeSet right = null;

        public BinarySearchTreeSet(Word word) {
            this.word = word;
        }

        /**
         * Recursive insertion method for adding elements to our BST.
         * @param word - the Word-object to add to our tree
         */
        public void add(Word word) {
            // add to the left branch of the tree
            if (word.compareTo(this.word) < 0) {
                if (left == null) {
                    left = new BinarySearchTreeSet(word);
                    size++;
                }
                else {
                    left.add(word);
                }
            }
            // add to the right branch of the tree
            else if (word.compareTo(this.word) > 0) {
                if (right == null) {
                    right = new BinarySearchTreeSet(word);
                    size++;
                }
                else {
                    right.add(word);
                }
            }
        }

        /**
         * Recursive search method to check if the argument exists in our tree.
         * @param word - the Word-object to search for
         * @return - whether the Word-object is inserted in our tree or not
         */
        public boolean contains(Word word) {
            // search the left branch of the tree
            if (word.compareTo(this.word) < 0) {
                if (left == null) {
                    return false;
                }
                else {
                    return left.contains(word);
                }
            }
            // search the right branch of the tree
            else if (word.compareTo(this.word) > 0) {
                if (right == null) {
                    return false;
                }
                else {
                    return right.contains(word);
                }
            }
            // if we get a match
            return true;
        }
    }

    // iterator for our TreeWordSet, inspiration gathered from the lecture and chapter 25.2 in the course literature
    private class TreeIterator implements Iterator<Word> {
        Word[] elements = new Word[size];
        int position = 0;
        int current = 0;

        public TreeIterator() {
            traverse();
        }

        private void traverse() {
            traverse(root);
        }

        // inorder traversal of the tree
        private void traverse(BinarySearchTreeSet root) {
            if (root.left != null) {
                traverse(root.left);
            }

            elements[position++] = root.word;

            if (root.right != null) {
                traverse(root.right);
            }
        }

        @Override
        public Word next() throws IndexOutOfBoundsException {
            return elements[current++];
        }

        // if there's more elements available to traverse
        @Override
        public boolean hasNext() {
            if (current < elements.length) {
                return true;
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
