package pl.ksr.extractors;

import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;

import java.util.List;

public class TFIDFTextExtractor {
    public static List<ExtractedData> extractFrom(List<Article> articles) {
        List<ExtractedData> extractedData = TFTextExtractor.extractFrom(articles);
        int amountOfDocuments = extractedData.size();

        System.out.println(extractedData.size());
        for (ExtractedData document : extractedData) {
            int allTermOccurrences = document.features.values().stream()
                    .reduce(0.0, Double::sum)
                    .intValue();

            for (String word : document.features.keySet()) {
                int inHowManyDocumentsWordExists =
                        extractedData.stream()
                                .filter(e -> e.features.keySet().contains(word))
                                .toArray().length;
                double idf = Math.log(amountOfDocuments / inHowManyDocumentsWordExists);
                document.features.compute(word, (key, value) -> value / allTermOccurrences * idf);
            }
        }
        return extractedData;
    }
}
