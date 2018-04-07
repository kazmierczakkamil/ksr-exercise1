package pl.ksr.metrics;

import java.util.Arrays;

public class CzebyszewMetric implements Metric {
    @Override
    public double getDistance(double[] a, double[] b) {
        if (a.length != b.length)
            throw new IllegalArgumentException();

        double[] distances = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            distances[i] = Math.abs(b[i] - a[i]);
        }
        return Arrays.stream(distances).max().getAsDouble();
    }
}
