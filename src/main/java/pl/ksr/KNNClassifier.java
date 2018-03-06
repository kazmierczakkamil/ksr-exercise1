package pl.ksr;

import pl.ksr.model.ClassifiableData;

import java.util.List;

public class KNNClassifier {
    int k;
    String[] labels;
    List<ClassifiableData> trainingData;
    List<ClassifiableData> testData;
}
