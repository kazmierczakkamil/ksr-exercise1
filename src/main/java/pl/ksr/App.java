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
        String[] labels = {"usa", "france", "uk", "canada", "japan", "west-germany"};

        for(int i=0; i<=21; ++i) {
            articles.addAll(
                TextDataService.getData("data/reut2-"+String.format("%03d", i)+".sgm", labels, LabelType.PLACE));
        }

        System.out.println("Extracting with TF...");
        long tfStart = System.currentTimeMillis();
        List<ExtractedData> tfExtracted = TFTextExtractor.extractFrom(articles);
        System.out.println("Extracted with TF in " + (System.currentTimeMillis() - tfStart) / 1000 + " ms");

        int splitIndex = (int)(tfExtracted.size()*0.6);
        KNNClassifier classifier1 = new KNNClassifier(5,labels,tfExtracted.subList(0,splitIndex));

        long knnTFStart = System.currentTimeMillis();
        classifier1.classify(tfExtracted.subList(splitIndex+1,tfExtracted.size()-1));
        System.out.println("Classified TF in " + (System.currentTimeMillis() - knnTFStart) / 1000 + " ms");

        System.out.println("Extracting with TF-IDF...");
        long tfidfStart = System.currentTimeMillis();
        List<ExtractedData> tfidfExtracted = TFIDFTextExtractor.extractFrom(articles);
        System.out.println("Extracted with TF-IDF in " + (System.currentTimeMillis() - tfidfStart) / 1000 + " ms");

        splitIndex = (int)(tfidfExtracted.size()*0.6);
        KNNClassifier classifier2 = new KNNClassifier(5,labels,tfidfExtracted.subList(0,splitIndex));
        long knnTFIDFStart = System.currentTimeMillis();
        classifier2.classify(tfidfExtracted.subList(splitIndex+1,tfidfExtracted.size()-1));
        System.out.println("Classified TF-IDF in " + (System.currentTimeMillis() - knnTFIDFStart) / 1000 + " ms");

    }

}
