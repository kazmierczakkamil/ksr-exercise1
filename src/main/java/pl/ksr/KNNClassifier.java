package pl.ksr;

import org.jooq.lambda.Seq;
import pl.ksr.model.ClassifiableData;
import pl.ksr.model.ExtractedData;
import pl.ksr.model.ResultData;
import pl.ksr.metrics.Metric;

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

    public List<ResultData> classify(List<ClassifiableData> testData) {
        List<ResultData> results = new ArrayList<>();
        for (ClassifiableData data : testData) {
            // results.add(new ResultData());
        }
        return results;
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

            List<String> labelsOfNearestNeighbors = nearestNeighbors.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(k)
                    .map(e -> e.getKey().label)
                    .collect(Collectors.toList());

            String label = Seq.of(labelsOfNearestNeighbors).mode().get().get(0);
            resultData.setAssignedLabel(label);
            results.add(resultData);
        });

        //======================


//        int i = 0;
//        for (ExtractedData documentToClassify : testData) {
//            ResultData resultData = new ResultData(documentToClassify);
//
//            Map<ExtractedData, Double> nearestNeighbors = new HashMap<>();
//
//            for (ExtractedData dataToCountDistance : trainingData) {
//                double distance = metric.getDistance(dataToCountDistance, documentToClassify);
//                nearestNeighbors.put(dataToCountDistance, distance);
//            }
//            List<String> labelsOfNearestNeighbors = nearestNeighbors.entrySet().stream()
//                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                    .limit(k)
//                    .map(e -> e.getKey().label)
//                    .collect(Collectors.toList());
//
//            String label = Seq.of(labelsOfNearestNeighbors).mode().get().get(0);
//            resultData.setAssignedLabel(label);
//            results.add(resultData);
//
//            System.out.println("Classified documents: " + i);
//            i++;
//        }
        return results;
    }
}



