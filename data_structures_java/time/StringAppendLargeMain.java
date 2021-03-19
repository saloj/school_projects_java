package time;

/**
 * Experiment - String append (80-char strings)
 */
public class StringAppendLargeMain {

    public static void main(String[] args) {
        // increased the JVM's memory in this experiment, got OutOfMemoryError.
        // still doesn't work - after researching it seems StringBuilder can hold 2147483647 characters, which is the
        // same as Integer.MAX_VALUE!

        // capacity of the SB after doing 17196620 appends is 1375731710 chars, the SB length is at 1375729600.
        // if we add more iterations, the capacity maxes out and SB doubles in size, but then it's > Integer.MAX_VALUE
        // (max capacity) and we get an error.
        // StringBuilder internally holds characters in a char[], and array has limitation in size (Integer.MAX_VALUE)

        StringBuilder test;
        String testString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZab";
        String testFinal = "";
        int numOfIterations = 17196620;

        long concats = 0;
        int totalSum = 0;
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

        long[] counters = { counter1, counter2, counter3, counter4, counter5, counter6, counter7, counter8, counter9,
                counter10 };

        // WARM-UP RUNS
        System.out.println("Warming up...");
        for (int testrun = 0; testrun < 5; testrun++) {
            concats = 0;
            testFinal = "";
            test = new StringBuilder();

            long before = System.currentTimeMillis();

            for (int x = 0; x < numOfIterations; x++) {
                test.append(testString);
                concats++;
            }
            testFinal = test.toString();

            long after = System.currentTimeMillis();

            System.out.println("Test run #" + (testrun + 1) + ": " + (after - before) + " milliseconds");
        }

        // ACTUAL RUNS
        System.out.println("Starting actual runs...");
        for (int i = 0; i < 10; i++) {
            concats = 0;
            testFinal = "";
            test = new StringBuilder();

            long before = System.currentTimeMillis();

            for (int x = 0; x < numOfIterations; x++) {
                test.append(testString);
                concats++;
            }
            testFinal = test.toString();

            long after = System.currentTimeMillis();

            System.out.println("Capacity: " + test.capacity() + " vs. Size: " + test.length());
            System.out.println("Actual run #" + (i + 1) + ": " + (after - before) + " milliseconds");
            counters[i] = after - before;
        }

        for (long nr : counters) {
            totalSum += nr;
        }

        int estimatedTime = totalSum / 10;

        System.out.println("=========================================================================");
        System.out.println("Result of append experiment experiment (10 runs) - adding 80 char strings");
        System.out.println("=========================================================================");
        System.out.println("Average time: " + estimatedTime + " milliseconds");
        System.out.println("String length: " + testFinal.length() + "\nNumber of appends: " + concats);
        System.out.print("Individual results (milliseconds): ");
        for (long nr : counters) {
            System.out.print(nr + " ");
        }
    }
}
