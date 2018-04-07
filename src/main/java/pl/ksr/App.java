package pl.ksr;

import org.apache.log4j.Logger;
import pl.ksr.extractors.Extractor;
import pl.ksr.extractors.TFIDFTextExtractor;
import pl.ksr.extractors.TFTextExtractor;
import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;
import pl.ksr.model.LabelType;
import pl.ksr.model.ResultData;
import pl.ksr.model.metrics.EuclideanMetric;
import pl.ksr.model.metrics.ManhattanMetric;
import pl.ksr.model.metrics.Metric;
import pl.ksr.services.TextDataService;
import pl.ksr.services.WordFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        List<Article> articles = new ArrayList<Article>();

        for (int i = 0; i <= 21; ++i) {
            articles.addAll(
                    TextDataService.getData("data/reut2-" + String.format("%03d", i) + ".sgm",
                            new String[]{"usa", "france", "uk", "canada", "japan", "west-germany"}, LabelType.PLACE));
        }


        int trainingDataSize = 8000;
        int k = 9;
        Metric metric = new EuclideanMetric();
        Extractor extractor = new TFIDFTextExtractor();

        long startExtraction = System.currentTimeMillis();
        log.info("Extraction started with " + extractor.getClass().getSimpleName() + "...");
        List<ExtractedData> extractedDataList = extractor.extractFrom(articles);
        long finishExtraction = System.currentTimeMillis();
        log.info("Reduced words: " + WordFilter.reducedWords);
        log.info("Extraction finished: " + (finishExtraction - startExtraction) / 1000 + " seconds");
        log.info("k: " + k);
        log.info("Metric: " + metric.getClass().getSimpleName());
        log.info("Documents: " + extractedDataList.size());

        List<ExtractedData> trainingData = extractedDataList.subList(0, trainingDataSize);
        List<ExtractedData> testData = extractedDataList.subList(trainingDataSize, extractedDataList.size());

        log.info("Training data: " + trainingData.size());
        log.info("Test data: " + testData.size());

        long startClassification = System.currentTimeMillis();
        KNNClassifier knn = new KNNClassifier(k, new String[]{}, trainingData);

        List<ResultData> classifiedDocuments = knn.classify(testData, metric);
        long finishClassification = System.currentTimeMillis();
        int correctlyClassifiedDocuments = 0;
        int incorrectlyClassifiedDocuments = 0;


        for (ResultData document : classifiedDocuments) {
            if (document.getAssignedLabel().equals(document.getExtractedData().label)) {
                correctlyClassifiedDocuments++;
            } else incorrectlyClassifiedDocuments++;
        }

        log.info("Classification finished: " + (finishClassification - startClassification) / 1000 + " seconds");
        log.info("Correctly classified documents: " + correctlyClassifiedDocuments);
        log.info("Incorrectly classified documents: " + incorrectlyClassifiedDocuments);
        log.info("Correct: " + String.format("%.2f", (double) correctlyClassifiedDocuments / classifiedDocuments.size() * 100) + "%\n");
    }

}
