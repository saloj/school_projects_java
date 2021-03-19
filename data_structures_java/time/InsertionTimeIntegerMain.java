package time;

import java.util.Random;

/**
 * Experiment - Sorting the largest possible int array (random values) in approximately 1 second using insertion sort.
 */
public class InsertionTimeIntegerMain {

    public static void main(String[] args) {
        long counter1 = 0;
        long counter2 = 0;
        long counter3 = 0;
        long counter4 = 0;
        long counter5 = 0;
        long counter6 = 0;
        long counter7 = 0;
        long counter8 = 0;
        long counter9 = 0;
        long counter10 = 0;
        int totalSum = 0;

        long[] counters = { counter1, counter2, counter3, counter4, counter5, counter6, counter7, counter8, counter9,
                counter10 };

        int[] testArrayInt;
        int[] sortedArrayInt;
        int sizeInt = 124500;
        int maxValueInt = 2000000;


        // WARM-UP RUNS
        System.out.println("Warming up...");
        for (int test = 0; test < 5; test++) {
            testArrayInt = randomIntArray(sizeInt,maxValueInt);

            long before = System.currentTimeMillis();

            sortedArrayInt = insertionSort(testArrayInt);

            long after = System.currentTimeMillis();

            System.out.println("Test run #" + (test + 1) + ": " + (after - before) + " milliseconds");
        }

        // ACTUAL RUNS
        System.out.println("Starting actual runs...");
        for (int i = 0; i < 10; i++) {
            // 124500 elements, range 2000000 to reduce the number of duplicates
            testArrayInt = randomIntArray(sizeInt,maxValueInt);

            long before = System.currentTimeMillis();

            sortedArrayInt = insertionSort(testArrayInt);

            long after = System.currentTimeMillis();

            System.out.println("Actual run #" + (i + 1) + ": " + (after - before) + " milliseconds");
            counters[i] = after - before;
        }

        for (long nr : counters) {
            totalSum += nr;
        }

        int estimatedTimeInt = totalSum / 10;

        System.out.println("=====================================================================");
        System.out.println("Result of sorting 10 arrays with random integers using insertion sort");
        System.out.println("=====================================================================");
        System.out.println("Number of ints in each array: " + sizeInt);
        System.out.println("Max value (range): " + maxValueInt);
        System.out.println("Sorted in average time: " + estimatedTimeInt + " milliseconds");
        System.out.print("Individual results (milliseconds): ");
        for (long nr : counters) {
            System.out.print(nr + " ");
        }
    }

    // helper method to generate randomized int array - using my code from assignment 3
    public static int[] randomIntArray(int size, int maxValue) {
        Random rnd = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = rnd.nextInt(maxValue) + 1;
        }

        return array;
    }

    // using my code from assignment 3
    public static int[] insertionSort(int[] in) {
        // new int array to keep original from being modified
        int[] array = new int[in.length];
        System.arraycopy(in, 0, array, 0, array.length);

        for (int firstUnsorted = 1; firstUnsorted < array.length; firstUnsorted++) {

            // first element in the unsorted partition (no unsorted elements to its left in the array)
            int temp = array[firstUnsorted];
            // declared outside of nested loop to be able to access it after the loop has finished executing
            int i;

            for (i = firstUnsorted; i > 0 && array[i - 1] > temp; i--) {
                // overwrite the element at position i with the element at i - 1 (no need to swap)
                array[i] = array[i - 1];
            }

            // put the current element being evaluated in place
            array[i] = temp;
        }

        return array;
    }
}
