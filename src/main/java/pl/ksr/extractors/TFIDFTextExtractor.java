package pl.ksr.extractors;

import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;
import pl.ksr.services.Lemmatizer;

import java.util.ArrayList;
import java.util.List;

public class TFIDFTextExtractor {
    public static List<ExtractedData> extractFrom(List<Article> articles) {
        List<ExtractedData> extractedData = new ArrayList<>();
        ExtractedData tmp = null;
        for(Article article : articles){
            String wordsBeforeLammatization = article.articleBody
                    .replaceAll("[^a-zA-Z ]", "").toLowerCase();
            List<String> words = Lemmatizer.getLemmasList(wordsBeforeLammatization);

            tmp = new ExtractedData(article.label);
            for(String word : words){
                if(tmp.features.containsKey(word))
                    tmp.features.put(word,tmp.features.get(word)+1);
                else tmp.features.put(word,1.0);
            }
            extractedData.add(tmp);
        }

        int amountOfDocuments = extractedData.size();
        int i = 0;
        for (ExtractedData document : extractedData) {
            int allTermOccurrences = document.features.values().parallelStream()
                    .reduce(0.0, Double::sum)
                    .intValue();

            long startTime = System.currentTimeMillis();
            for (String word : document.features.keySet()) {
                long inHowManyDocumentsWordExists =
                        extractedData.parallelStream()
                                .filter(e -> e.features.keySet().contains(word))
                                .count();
                double idf = Math.log(amountOfDocuments / inHowManyDocumentsWordExists);
                document.features.compute(word, (key, value) -> value / allTermOccurrences * idf);
            }
            System.out.println("Count occurences: " + (System.currentTimeMillis() - startTime));
            i++;
            System.out.println(i);
        }
        return extractedData;
    }
}
