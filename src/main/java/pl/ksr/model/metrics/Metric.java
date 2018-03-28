package pl.ksr.model.metrics;

import pl.ksr.model.ExtractedData;

public interface Metric {
    double getDistance(ExtractedData a, ExtractedData b);
}
