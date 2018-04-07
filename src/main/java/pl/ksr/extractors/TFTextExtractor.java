package pl.ksr.extractors;

import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;
import pl.ksr.services.Lemmatizer;
import pl.ksr.services.WordFilter;

import java.util.ArrayList;
import java.util.List;

public class TFTextExtractor implements Extractor {

    public List<ExtractedData> extractFrom(List<Article> articles){
        List<ExtractedData> extractedData = new ArrayList<>();
        ExtractedData tmp = null;
        for(Article article : articles){
            String wordsBeforeLemmatization = article.articleBody
                    .replaceAll("[^a-zA-Z ]", "").toLowerCase();
            List<String> wordsBeforeFiltering = Lemmatizer.getLemmasList(wordsBeforeLemmatization);
            List<String> words = WordFilter.filter(wordsBeforeFiltering);

            tmp = new ExtractedData(article.label);
            for(String word : words){
                if(tmp.features.containsKey(word))
                    tmp.features.put(word,tmp.features.get(word)+1);
                else tmp.features.put(word,1.0);
            }
            extractedData.add(tmp);
        }
        System.out.println(extractedData.size());
        return extractedData;
    }
}
