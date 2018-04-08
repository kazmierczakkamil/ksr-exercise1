package pl.ksr.metrics;

import pl.ksr.extractors.NgramExtractor;
import pl.ksr.model.ExtractedData;

import java.util.Set;

public class NgramMetric implements Metric {

    private int n;

    public NgramMetric(int n) {
        this.n = n;
    }

    public double getDistance(ExtractedData a, ExtractedData b) {
        double numberOfCommonNgrams = 0;
        Set<String> keySet = a.features.keySet();
        for(String key : keySet)
            if(b.features.containsKey(key))
                numberOfCommonNgrams++;
        return numberOfCommonNgrams / (keySet.size() - n + 1);
    }
}
