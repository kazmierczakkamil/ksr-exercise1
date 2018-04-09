package pl.ksr;

import org.jooq.lambda.Seq;
import pl.ksr.metrics.Metric;
import pl.ksr.model.ExtractedData;
import pl.ksr.model.ResultData;

import java.util.*;
import java.util.stream.Collectors;

public class KNNClassifier {
    int k;
    String[] labels;
    List<ExtractedData> trainingData;

    KNNClassifier(int k, String[] labels, List<ExtractedData> trainingData) {
        this.k = k;
        this.labels = labels;
        this.trainingData = trainingData;
    }

    public List<ResultData> classify(List<ExtractedData> testData, Metric metric) {
        List<ResultData> results = new ArrayList<>();

        testData.parallelStream().forEach(documentToClassify -> {
            ResultData resultData = new ResultData(documentToClassify);
            Map<ExtractedData, Double> nearestNeighbors = new HashMap<>();

            for (ExtractedData dataToCountDistance : trainingData) {
                double distance = metric.getDistance(dataToCountDistance, documentToClassify);
                nearestNeighbors.put(dataToCountDistance, distance);
            }

            List<String> labelsOfNearestNeighbors = nearestNeighbors.entrySet().parallelStream()
                    .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                    .limit(k)
                    .map(e -> e.getKey().label)
                    .collect(Collectors.toList());

            String label = Seq.of(labelsOfNearestNeighbors).mode().get().get(0);
            resultData.setAssignedLabel(label);
            results.add(resultData);
        });
        return results;
    }
}



