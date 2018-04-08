package pl.ksr.extractors;

import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;
import pl.ksr.services.Lemmatizer;
import pl.ksr.services.WordFilter;

import java.util.ArrayList;
import java.util.List;

public class NgramExtractor implements Extractor {

    private int n;

    public NgramExtractor(int n) {
        this.n = n;
    }

    public List<ExtractedData> extractFrom(List<Article> articles) {
        List<ExtractedData> extractedData = new ArrayList<>();
        ExtractedData tmp;
        StringBuilder stringBuilder;
        String key;
        String joinedWords;
        char[] chars;
        for(Article article : articles) {
            String wordsBeforeLemmatization = article.articleBody
                    .replaceAll("[^a-zA-Z ]", "").toLowerCase();
            List<String> wordsBeforeFiltering = Lemmatizer.getLemmasList(wordsBeforeLemmatization);
            List<String> words = WordFilter.filter(wordsBeforeFiltering);

            joinedWords = String.join("",words);
            chars = joinedWords.toCharArray();

            tmp = new ExtractedData(article.label);
            for(int i = 0; i <= chars.length - n; ++i) {
                stringBuilder = new StringBuilder();
                for(int j = i; j < i + n; ++j) stringBuilder.append(chars[j]);
                key = stringBuilder.toString();
                if(tmp.features.containsKey(key))
                    tmp.features.put(key, tmp.features.get(key) + 1);
                else tmp.features.put(key, 1.0);
            }
            extractedData.add(tmp);
        }
        return extractedData;
    }
}
