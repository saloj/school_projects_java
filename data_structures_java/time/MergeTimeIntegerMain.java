package time;

import java.util.Random;

/**
 * Experiment - Sorting the largest possible int array (random values) in approximately 1 second using merge sort.
 */
public class MergeTimeIntegerMain {

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
        int sizeInt = 6150000;
        int maxValueInt = 12000000;

        // WARM-UP RUNS
        System.out.println("Warming up...");
        for (int test = 0; test < 5; test++) {
            testArrayInt = randomIntArray(sizeInt,maxValueInt);

            long before = System.currentTimeMillis();

            sortedArrayInt = mergeSort(testArrayInt);

            long after = System.currentTimeMillis();

            System.out.println("Test run #" + (test + 1) + ": " + (after - before) + " milliseconds");
        }

        // ACTUAL RUNS
        System.out.println("Starting actual runs...");
        for (int i = 0; i < 10; i++) {
            // 6200000 elements, range 12000000 to reduce the number of duplicates
            testArrayInt = randomIntArray(sizeInt,maxValueInt);

            long before = System.currentTimeMillis();

            sortedArrayInt = mergeSort(testArrayInt);

            long after = System.currentTimeMillis();

            System.out.println("Actual run #" + (i + 1) + ": " + (after - before) + " milliseconds");
            counters[i] = after - before;
        }

        for (long nr : counters) {
            totalSum += nr;
        }

        int estimatedTimeInt = totalSum / 10;

        System.out.println("=====================================================================");
        System.out.println("Result of sorting 10 arrays with random integers using merge sort");
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
    public static int[] mergeSort(int[] in) {
        int[] array = new int[in.length];
        System.arraycopy(in, 0, array, 0, array.length);

        mergeAndSortInt(array);
        return array;
    }

    // method that handles the actual splitting
    public static void mergeAndSortInt(int[] in) {

        if (in.length > 1) {

            int[] firstArray = new int[in.length / 2];
            System.arraycopy(in, 0, firstArray, 0, in.length / 2);
            mergeAndSortInt(firstArray);

            int[] secondArray = new int[in.length - in.length / 2];
            System.arraycopy(in, in.length / 2, secondArray, 0, secondArray.length);
            mergeAndSortInt(secondArray);

            merge(firstArray, secondArray, in);
        }
    }

    // method for merging
   public static void merge(int[] first, int[] second, int[] tempArray) {
        int currentIndexFirst = 0;
        int currentIndexSecond = 0;
        int currentIndexTemp = 0;

        while (currentIndexFirst < first.length && currentIndexSecond < second.length) {
            if (first[currentIndexFirst] < second[currentIndexSecond]) {
                tempArray[currentIndexTemp++] = first[currentIndexFirst++];
            }
            else {
                tempArray[currentIndexTemp++] = second[currentIndexSecond++];
            }
        }

        while (currentIndexFirst < first.length) {
            tempArray[currentIndexTemp++] = first[currentIndexFirst++];
        }

        while (currentIndexSecond < second.length) {
            tempArray[currentIndexTemp++] = second[currentIndexSecond++];
        }
    }
}
