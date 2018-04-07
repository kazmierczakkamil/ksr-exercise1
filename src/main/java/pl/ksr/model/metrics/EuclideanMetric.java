package pl.ksr.model.metrics;

public class EuclideanMetric implements Metric {
    @Override
    public double getDistance(double[] a, double[] b) {
        double distance = 0.0;

        for (int i = 0; i < a.length; i++) {
            distance += Math.sqrt(Math.pow(b[i] - a[i], 2));
        }
        return distance;
    }
}
