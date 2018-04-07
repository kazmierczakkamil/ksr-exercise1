package pl.ksr;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {
        List<Article> articles = new ArrayList<Article>();

        for (int i = 0; i <= 21; ++i) {
            articles.addAll(
                    TextDataService.getData("data/reut2-" + String.format("%03d", i) + ".sgm",
                            new String[]{"usa", "france", "uk", "canada", "japan", "west-germany"}, LabelType.PLACE));
        }


        int trainingDataSize = 8000;
        int k = 15;
        Metric metric = new ManhattanMetric();
        Extractor extractor = new TFIDFTextExtractor();

        long startExtraction = System.currentTimeMillis();
        System.out.println("Extraction started with " + extractor.getClass().getSimpleName() + "...");
        List<ExtractedData> extractedDataList = extractor.extractFrom(articles);
        long finishExtraction = System.currentTimeMillis();
        System.out.println("Extraction finished: " + (finishExtraction - startExtraction) / 1000 + " seconds");
        System.out.println("Classification started...");
        System.out.println("k: " + k);
        System.out.println("Metric: " + metric.getClass().getSimpleName());
        System.out.println("Documents: " + extractedDataList.size());

        List<ExtractedData> trainingData = extractedDataList.subList(0, trainingDataSize);
        List<ExtractedData> testData = extractedDataList.subList(trainingDataSize, extractedDataList.size());

        System.out.println("Training data: " + trainingData.size());
        System.out.println("Test data: " + testData.size());

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

        System.out.println("Classification finished: " + (finishClassification - startClassification) / 1000 + " seconds");
        System.out.println("Correctly classified documents: " + correctlyClassifiedDocuments);
        System.out.println("Incorrectly classified documents: " + incorrectlyClassifiedDocuments);
        System.out.println("Correct: " + String.format("%.2f", (double) correctlyClassifiedDocuments / classifiedDocuments.size() * 100) + "%");
    }

}
