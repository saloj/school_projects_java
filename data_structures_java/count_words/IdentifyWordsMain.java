package count_words;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IdentifyWordsMain {

    public static void main(String[] args) {
        File historyOfProgramming = new File("./assignment-4/count_words/HistoryOfProgramming.txt");
        File fileToWrite = new File("./assignment-4/count_words/words.txt");

        StringBuilder text = new StringBuilder();

        try {
            if (!historyOfProgramming.exists()) {
                throw new IOException("File does not exist.");
            }

            Scanner scan = new Scanner(historyOfProgramming);
            PrintWriter printer = new PrintWriter(fileToWrite);

            // replaces anything that's not a letter or whitespace
            String regex = "[^A-Za-z\\s]";

            while (scan.hasNext()) {
                String str = scan.nextLine().replaceAll(regex, "");
                text.append(str);
                text.append("\n");
            }

            printer.print(text);

            printer.close();
            scan.close();
        }
        catch (IOException err) {
            err.printStackTrace();
        }
    }
}
