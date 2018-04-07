package pl.ksr.metrics;

import pl.ksr.model.ExtractedData;

import java.util.HashSet;
import java.util.Set;

public class ManhattanMetric implements Metric {
    @Override
    public double getDistance(ExtractedData a, ExtractedData b) {
        double distance = 0.0;

//        for (int i = 0; i < a.length; i++) {
//            distance += Math.abs(b[i] - a[i]);
//        }
        Set<String> setOfKeys = new HashSet<>(a.features.keySet());
        setOfKeys.addAll(b.features.keySet());

//        for (int i = 0; i < setOfKeys.size(); i++) {
//            distance += Math.sqrt(Math.pow(b[i] - a[i], 2));
//        }

        for (String key : setOfKeys) {
            double aValue = a.features.getOrDefault(key, 0.0);
            double bValue = b.features.getOrDefault(key, 0.0);
            distance += Math.abs(aValue - bValue);
        }


        return distance;
    }
}
