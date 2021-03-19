package time;

import java.util.Comparator;
import java.util.Random;

/**
 * Experiment - Sorting the largest possible String array (10 random chars/string) in approximately 1 second
 * using merge sort.
 */
public class MergeTimeStringMain {

    public static void main(String[] args) {
        Comparator<String> comp = String.CASE_INSENSITIVE_ORDER;

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

        String[] testArrayString;
        String[] sortedArrayString;
        int sizeString = 1075000;

        // WARM-UP RUNS
        System.out.println("Warming up...");
        for (int test = 0; test < 5; test++) {
            testArrayString = randomStringArray(sizeString);

            long before = System.currentTimeMillis();

            sortedArrayString = mergeSort(testArrayString, comp);

            long after = System.currentTimeMillis();

            System.out.println("Test run #" + (test + 1) + ": " + (after - before) + " milliseconds");
        }

        // ACTUAL RUNS
        System.out.println("Starting actual runs...");
        for (int i = 0; i < 10; i++) {
            // 1075000 elements, 10 characters in each String
            testArrayString = randomStringArray(sizeString);

            long before = System.currentTimeMillis();

            sortedArrayString = mergeSort(testArrayString, comp);

            long after = System.currentTimeMillis();

            System.out.println("Actual run #" + (i + 1) + ": " + (after - before) + " milliseconds");
            counters[i] = after - before;
        }

        for (long nr : counters) {
            totalSum += nr;
        }

        int estimatedTimeString = totalSum / 10;

        System.out.println("========================================================================================");
        System.out.println("Result of sorting 10 arrays with random Strings with 10 characters each using merge sort");
        System.out.println("========================================================================================");
        System.out.println("Number of Strings in each array: " + sizeString);
        System.out.println("Sorted in average time: " + estimatedTimeString + " milliseconds");
        System.out.print("Individual results (milliseconds): ");
        for (long nr : counters) {
            System.out.print(nr + " ");
        }
    }

    // helper method to generate randomized String array - using my code from assignment 3
    // each string should contain 10 random characters according to the assignment instructions
    public static String[] randomStringArray(int size) {
        Random rnd = new Random();
        String[] array = new String[size];
        StringBuilder temp;

        for (int i = 0; i < size; i++) {
            temp = new StringBuilder();
            char str1 = (char) ((char) rnd.nextInt((90 - 65) + 1) + 65); // capitalized
            char str2 = (char) ((char) rnd.nextInt((122 - 97) + 1) + 97); // non-cap
            char str3 = (char) ((char) rnd.nextInt((90 - 65) + 1) + 65); // capitalized
            char str4 = (char) ((char) rnd.nextInt((122 - 97) + 1) + 97); // non-cap
            char str5 = (char) ((char) rnd.nextInt((90 - 65) + 1) + 65); // capitalized
            char str6 = (char) ((char) rnd.nextInt((122 - 97) + 1) + 97); // non-cap
            char str7 = (char) ((char) rnd.nextInt((90 - 65) + 1) + 65); // capitalized
            char str8 = (char) ((char) rnd.nextInt((122 - 97) + 1) + 97); // non-cap
            char str9 = (char) ((char) rnd.nextInt((90 - 65) + 1) + 65); // capitalized
            char str10 = (char) ((char) rnd.nextInt((122 - 97) + 1) + 97); // non-cap

            if (i % 2 == 0) {
                temp.append(str2);
                temp.append(str1);
                temp.append(str4);
                temp.append(str3);
                temp.append(str6);
                temp.append(str5);
                temp.append(str8);
                temp.append(str7);
                temp.append(str10);
                temp.append(str9);
            }
            else {
                temp.append(str1);
                temp.append(str2);
                temp.append(str3);
                temp.append(str4);
                temp.append(str5);
                temp.append(str6);
                temp.append(str7);
                temp.append(str8);
                temp.append(str9);
                temp.append(str10);
            }

            array[i] = temp.toString();
            temp = null;
        }

        return array;
    }

    // using my code from assignment 3
    public static String[] mergeSort(String[] in, Comparator<String> c) {
        String[] array = new String[in.length];
        System.arraycopy(in, 0, array, 0, array.length);

        mergeAndSortString(array, c);
        return array;
    }

    // method that handles the actual splitting
    public static void mergeAndSortString(String[] in, Comparator<String> c) {

        if (in.length > 1) {
            String[] firstArray = new String[in.length / 2];
            System.arraycopy(in, 0, firstArray, 0, in.length / 2);
            mergeAndSortString(firstArray, c);

            String[] secondArray = new String[in.length - in.length / 2];
            System.arraycopy(in, in.length / 2, secondArray, 0, secondArray.length);
            mergeAndSortString(secondArray, c);

            merge(firstArray, secondArray, in, c);
        }
    }

    // method for merging
    public static void merge(String[] first, String[] second, String[] tempArray, Comparator<String> c) {
        int currentIndexFirst = 0;
        int currentIndexSecond = 0;
        int currentIndexTemp = 0;

        while (currentIndexFirst < first.length && currentIndexSecond < second.length) {
            if (c.compare(first[currentIndexFirst], second[currentIndexSecond]) < 0) {
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
