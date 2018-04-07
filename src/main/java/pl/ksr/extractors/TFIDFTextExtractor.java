package pl.ksr.extractors;

import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;
import pl.ksr.services.Lemmatizer;
import pl.ksr.services.WordFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDFTextExtractor implements Extractor {
    public List<ExtractedData> extractFrom(List<Article> articles) {
        List<ExtractedData> extractedData = new ArrayList<>();
        Map<String, Double> occurrencesInAllDocuments = new HashMap<>();
        ExtractedData tmp = null;
        for (Article article : articles) {
            String wordsBeforeLemmatization = article.articleBody
                    .replaceAll("[^a-zA-Z ]", "").toLowerCase();
            List<String> wordsBeforeFiltering = Lemmatizer.getLemmasList(wordsBeforeLemmatization);
            List<String> words = WordFilter.filter(wordsBeforeFiltering);

            tmp = new ExtractedData(article.label);
            for (String word : words) {
                if (tmp.features.containsKey(word))
                    tmp.features.put(word, tmp.features.get(word) + 1);
                else {
                    tmp.features.put(word, 1.0);
                    if (occurrencesInAllDocuments.containsKey(word))
                        occurrencesInAllDocuments.put(word, occurrencesInAllDocuments.get(word) + 1);
                    else occurrencesInAllDocuments.put(word, 1.0);
                }
            }
            extractedData.add(tmp);
        }

        int amountOfDocuments = extractedData.size();

        for (ExtractedData document : extractedData) {
            int allTermOccurrences = document.features.values().parallelStream()
                    .reduce(0.0, Double::sum)
                    .intValue();

            for (String word : document.features.keySet()) {
                double idf = Math.log(amountOfDocuments / occurrencesInAllDocuments.get(word));
                document.features.compute(word, (key, value) -> value / allTermOccurrences * idf);
            }
        }
        return extractedData;
    }
}
