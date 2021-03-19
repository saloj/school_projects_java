package count_words;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class WordCount1Main {

    public static void main(String[] args) {
        File fileToRead = new File("./assignment-4/count_words/words.txt");
        HashSet<Word> hash = new HashSet<>();
        TreeSet<Word> tree = new TreeSet<>();

        try {
            if (!fileToRead.exists()) {
                throw new IOException("File does not exist.");
            }

            Scanner scan = new Scanner(fileToRead);

            while (scan.hasNext()) {
                String str = scan.nextLine();
                String[] array = str.split("\\s");

                for (String word : array) {
                    Word theWord = new Word(word);
                    hash.add(theWord);
                    tree.add(theWord);
                }
            }

            scan.close();
        }
        catch (IOException err) {
            err.printStackTrace();
        }

        // size is 350 for hash & tree
        System.out.println("HashSet size: " + hash.size());
        System.out.println("TreeSet size: " + tree.size());
    }
}
