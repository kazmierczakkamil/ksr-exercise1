package pl.ksr;

import pl.ksr.extractors.TFIDFTextExtractor;
import pl.ksr.extractors.TFTextExtractor;
import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;
import pl.ksr.model.LabelType;
import pl.ksr.services.TextDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import static pl.ksr.extractors.TFIDFTextExtractor.extractFrom;

public class App {

    public static void main(String[] args) throws IOException {
        List<Article> articles = new ArrayList<Article>();

        for(int i=0; i<=21; ++i) {
            articles.addAll(
                TextDataService.getData("reut2-"+String.format("%03d", i)+".sgm",
                                        new String[]{"usa", "argentina"}, LabelType.PLACE));
        }


        long tfStart = System.currentTimeMillis();
        List<ExtractedData> tf = TFTextExtractor.extractFrom(articles);
        System.out.println("TF: " + (System.currentTimeMillis() - tfStart) / 1000);

        long tfidfStart = System.currentTimeMillis();
        List<ExtractedData> tfidf = TFIDFTextExtractor.extractFrom(articles);
        System.out.println("TF-IDF: " + (System.currentTimeMillis() - tfidfStart) / 1000);

    }

}
