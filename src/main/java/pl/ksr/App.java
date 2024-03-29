package pl.ksr;

import org.apache.log4j.Logger;
import pl.ksr.extractors.Extractor;
import pl.ksr.extractors.NgramExtractor;
import pl.ksr.extractors.TFIDFTextExtractor;
import pl.ksr.metrics.JaccardMetric;
import pl.ksr.metrics.NgramMetric;
import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;
import pl.ksr.model.LabelType;
import pl.ksr.model.ResultData;
import pl.ksr.metrics.EuclideanMetric;
import pl.ksr.metrics.Metric;
import pl.ksr.services.TextDataService;
import pl.ksr.services.WordFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class App {

    static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) throws IOException {

        List<Article> articles = Configs.getData();
        int trainingDataSize = (int)(Configs.getTrainingDataPercentage() / 100.0 * articles.size());
        int k = Configs.getK();
        Metric metric = Configs.getMetric();
        Extractor extractor = Configs.getExtractor();
        int limit = Configs.getLimit();

        long startExtraction = System.currentTimeMillis();
        log.info("Extraction started with " + extractor.getClass().getSimpleName() + "...");
        List<ExtractedData> extractedDataList = extractor.extractFrom(articles);
        long finishExtraction = System.currentTimeMillis();
        log.info("Reduced words: " + WordFilter.reducedWords);
        log.info("Extraction finished: " + (finishExtraction - startExtraction) / 1000 + " seconds");
        log.info("k: " + k);
        log.info("Metric: " + metric.getClass().getSimpleName());
        log.info("Documents: " + extractedDataList.size());
        log.info("Limit: " + limit);

        List<ExtractedData> trainingData = extractedDataList.subList(0, trainingDataSize);
        List<ExtractedData> testData = extractedDataList.subList(trainingDataSize, extractedDataList.size());

        log.info("Training data: " + trainingData.size());
        log.info("Test data: " + testData.size());

        long startClassification = System.currentTimeMillis();
        KNNClassifier knn = new KNNClassifier(k, new String[]{}, trainingData);

        List<ResultData> classifiedDocuments = knn.classify(testData, metric, 20);
        System.out.println("Classified documents: " + classifiedDocuments.size());
        long finishClassification = System.currentTimeMillis();
        int correctlyClassifiedDocuments = 0;
        int incorrectlyClassifiedDocuments = 0;


        for (ResultData document : classifiedDocuments) {
            if (document.getAssignedLabel().equals(
                document.getExtractedData().label)) {
                correctlyClassifiedDocuments++;
            } else incorrectlyClassifiedDocuments++;
        }

        log.info("Classification finished: " + (finishClassification - startClassification) / 1000 + " seconds");
        log.info("Correctly classified documents: " + correctlyClassifiedDocuments);
        log.info("Incorrectly classified documents: " + incorrectlyClassifiedDocuments);
        log.info("Correct: " + String.format("%.2f", (double) correctlyClassifiedDocuments / classifiedDocuments.size() * 100) + "%\n");
    }

}
