package pl.ksr.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordFilter {
    static List<String> redundantWords = new ArrayList<>();
    static public int reducedWords = 0;

    static {
        try {
            Files.lines(Paths.get("data/stopwords.txt")).forEach(line -> redundantWords.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> filter(List<String> textToFilter) {
        List<String> filteredText = new ArrayList<>(textToFilter);
        filteredText.removeAll(WordFilter.redundantWords);
        reducedWords += textToFilter.size() - filteredText.size();
        return filteredText;
    }
}
