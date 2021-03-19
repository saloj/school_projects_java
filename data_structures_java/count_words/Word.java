package count_words;


public class Word implements Comparable<Word> {
    private String word;

    public Word(String str) {
        this.word = str;
    }

    public String toString() {
        return word;
    }

    @Override
    public int hashCode() {
        // Strings hashCode method is the better choice, cast to lower case to avoid duplicates
        return word.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Word) {
            // equality indifferent of casing
            return word.equalsIgnoreCase(other.toString());
        }
        return false;
    }

    @Override
    public int compareTo(Word o) {
        return word.compareToIgnoreCase(o.toString());
    }
}
