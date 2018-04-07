package pl.ksr.metrics;

import pl.ksr.model.ExtractedData;

public interface Metric {
    double getDistance(ExtractedData a, ExtractedData b);
}
