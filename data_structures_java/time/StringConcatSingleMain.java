package time;

/**
 * Experiment - String concatenation (1-char strings)
 */
public class StringConcatSingleMain {

    public static void main(String[] args) {
        String testString = "a";
        String testFinal = "";
        int numOfIterations = 119000;

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

            long before = System.currentTimeMillis();

            for (int x = 0; x < numOfIterations; x++) {
                testFinal += testString;
                concats++;
            }

            long after = System.currentTimeMillis();

            System.out.println("Test run #" + (testrun + 1) + ": " + (after - before) + " milliseconds");
        }

        // ACTUAL RUNS
        System.out.println("Starting actual runs...");
        for (int i = 0; i < 10; i++) {
            concats = 0;
            testFinal = "";

            long before = System.currentTimeMillis();

            for (int x = 0; x < numOfIterations; x++) {
                testFinal += testString;
                concats++;
            }

            long after = System.currentTimeMillis();

            System.out.println("Actual run #" + (i + 1) + ": " + (after - before) + " milliseconds");
            counters[i] = after - before;
        }

        for (long nr : counters) {
            totalSum += nr;
        }

        int estimatedTime = totalSum / 10;

        System.out.println("======================================================================");
        System.out.println("Result of concatenation experiment (10 runs) - adding 1 char strings");
        System.out.println("======================================================================");
        System.out.println("Average time: " + estimatedTime + " milliseconds");
        System.out.println("String length: " + testFinal.length() + "\nNumber of concatenations: " + concats);
        System.out.print("Individual results (milliseconds): ");
        for (long nr : counters) {
            System.out.print(nr + " ");
        }
    }
}
