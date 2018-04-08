package pl.ksr.metrics;

import pl.ksr.model.ExtractedData;

import java.util.HashSet;
import java.util.Set;

public class CosDistance implements Metric {

    public double getDistance(ExtractedData a, ExtractedData b) {
        double similarity = 0.0;
        double distance = 0.0;
        double a1 = 0.0;
        double b1 = 0.0;
        double c1 = 0.0;

        Set<String> setOfKeys = new HashSet<>(a.features.keySet());
        setOfKeys.addAll(b.features.keySet());

//        for (int i = 0; i < setOfKeys.size(); i++) {
//            distance += Math.sqrt(Math.pow(b[i] - a[i], 2));
//        }

        for (String key : setOfKeys) {
            double aValue = a.features.getOrDefault(key, 0.0);
            double bValue = b.features.getOrDefault(key, 0.0);
            a1 += aValue * bValue;
            b1 += Math.pow(aValue, 2);
            c1 += Math.pow(bValue, 2);
        }

        similarity = a1 / (Math.sqrt(b1) * Math.sqrt(c1));
        distance = 2 * Math.acos(similarity) / Math.PI;

        return distance;
    }
}
