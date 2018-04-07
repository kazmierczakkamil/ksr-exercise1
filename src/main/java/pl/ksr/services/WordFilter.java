package pl.ksr.services;

import java.util.ArrayList;
import java.util.List;

public class WordFilter {
    static List<String> redundantWords = new ArrayList<>();
    static public int reducedWords = 0;

    static {
        redundantWords.add("the");
        redundantWords.add("of");
        redundantWords.add("as");
        redundantWords.add("a");
        redundantWords.add("an");
    }

    public static List<String> filter(List<String> textToFilter) {
        List<String> filteredText = new ArrayList<>(textToFilter);
        filteredText.removeAll(WordFilter.redundantWords);
        reducedWords += textToFilter.size() - filteredText.size();
        return filteredText;
    }
}
