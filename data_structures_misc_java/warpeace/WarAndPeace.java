package warpeace;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class WarAndPeace {
    public static void main(String[] args) {
        String text = readText("./assignment-2/warpeace/WarAndPeace.txt");
        String[] words = text.split(" ");

        System.out.println("Initial word count: " + words.length); // returns 577091 as the exercise instruction did
        String regex = "[^a-zA-Z'-]"; // look for words NOT containing only a-z/A-Z/chars ' & - (to replace)

        Stream<String> stream = Arrays.stream(words); // adding <String> since IntelliJ complained with "raw" Stream
        int numberOfWords = (int) stream.map(s -> s.replaceAll(regex, "")).distinct().count();
        // transform each word in accordance with the regex and count the number of distinct occurrences

        System.out.println("Unique words found: " + numberOfWords); // 21782 unique words
    }

    /**
     * Take the path to a file as argument which is then read and converted to String.
     * @param file the path of the file we're extracting to String
     * @return String representation of the file's content
     */
    public static String readText(String file) {
        StringBuilder buf = new StringBuilder();

        try {
            Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8);
            stream.forEach(s -> buf.append(s).append(" "));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return buf.toString();
    }
}
