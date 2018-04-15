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

    public List<ResultData> classify(List<ExtractedData> testData, Metric metric, int limitSize) {
        List<ResultData> results = new ArrayList<>();
        // sorting and cutting
        if (limitSize != 0) {
            List<ExtractedData> modifiedTrainingData = new ArrayList<>();
            List<ExtractedData> modifiedTestData = new ArrayList<>();
            for (ExtractedData data : testData) {
                ExtractedData tmp = new ExtractedData(data.label);
                tmp.features = data.features.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(limitSize)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                modifiedTestData.add(tmp);
            }

            for (ExtractedData data : trainingData) {
                ExtractedData tmp = new ExtractedData(data.label);
                tmp.features = data.features.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(limitSize)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                modifiedTrainingData.add(tmp);
            }

            testData = modifiedTestData;
            trainingData = modifiedTrainingData;
        }

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



