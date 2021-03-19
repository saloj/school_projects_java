package count_words;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WordCount2Main {

    public static void main(String[] args) {
        File fileToRead = new File("./assignment-4/count_words/words.txt");
        HashWordSet hash = new HashWordSet();
        TreeWordSet tree = new TreeWordSet();

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
        System.out.println("HashWordSet size: " + hash.size());
        System.out.println(hash.toString());

        // size is 350 for hash & tree
        System.out.println("TreeWordSet size: " + tree.size());
        System.out.println(tree.toString());
    }
}
