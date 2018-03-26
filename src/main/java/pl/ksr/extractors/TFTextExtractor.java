package pl.ksr.extractors;

import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;

import java.util.ArrayList;
import java.util.List;

public class TFTextExtractor {

    public static List<ExtractedData> extractFrom(List<Article> articles){
        List<ExtractedData> extractedData = new ArrayList<>();
        ExtractedData tmp = null;
        for(Article article : articles){
            String[] words = article.articleBody
                    .replaceAll("[^a-zA-Z ]", "").toLowerCase()
                    .split(" ");
            tmp = new ExtractedData(article.label);
            for(String word : words){
                if(tmp.features.containsKey(word))
                    tmp.features.put(word,tmp.features.get(word)+1);
                else tmp.features.put(word,1.0);
            }
            extractedData.add(tmp);
        }
        return extractedData;
    }
}
