package pl.ksr.metrics;

import pl.ksr.model.ExtractedData;

import java.util.HashSet;
import java.util.Set;

public class JaccardMetric implements Metric {

    public double getDistance(ExtractedData a, ExtractedData b) {
        double intersection = 0, union = 0;
        double aValue, bValue;

        Set<String> setOfKeys = new HashSet<>(a.features.keySet());
        setOfKeys.addAll(b.features.keySet());

        for(String key : setOfKeys) {
            aValue = a.features.getOrDefault(key,0.0);
            bValue = b.features.getOrDefault(key,0.0);
            intersection += Math.min(aValue,bValue);
            union += Math.max(aValue,bValue);
        }

        return 1 - intersection / union;
    }
}
