package pl.ksr;

import pl.ksr.model.ClassifiableData;
import pl.ksr.model.ExtractedData;
import pl.ksr.model.ResultData;

import java.util.ArrayList;
import java.util.List;

public class KNNClassifier {
    int k;
    String[] labels;
    List<ExtractedData> trainingData;

    KNNClassifier(int k, String[] labels,List<ExtractedData> trainingData){
        this.k = k;
        this.labels = labels;
        this.trainingData = trainingData;
    }

    public List<ResultData> classify(List<ExtractedData> testData){
        List<ResultData> results = new ArrayList<>();
        for(ExtractedData data : testData){
            results.add(new ResultData());
        }
        return results;
    }


}
