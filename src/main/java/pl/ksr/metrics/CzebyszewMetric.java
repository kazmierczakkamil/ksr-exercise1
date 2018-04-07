package pl.ksr.metrics;

import pl.ksr.model.ExtractedData;

import java.util.*;

public class CzebyszewMetric implements Metric {
    @Override
    public double getDistance(ExtractedData a, ExtractedData b) {

//        if (a.length != b.length)
//            throw new IllegalArgumentException();
//
//        double[] distances = new double[a.length];
//        for (int i = 0; i < a.length; i++) {
//            distances[i] = Math.abs(b[i] - a[i]);
//        }
//        return Arrays.stream(distances).max().getAsDouble();
        Set<String> setOfKeys = new HashSet<>(a.features.keySet());
        setOfKeys.addAll(b.features.keySet());

        List<Double> distances = new ArrayList<>();

        for (String key : setOfKeys) {
            double aValue = a.features.getOrDefault(key, 0.0);
            double bValue = b.features.getOrDefault(key, 0.0);
            distances.add(Math.abs(aValue - bValue));
        }

        return Collections.max(distances);
    }
}
