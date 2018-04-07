package pl.ksr.model.data;

public class ManhattanMetric implements Metric {
    @Override
    public double getDistance(double[] a, double[] b) {
        double distance = 0.0;

        for (int i = 0; i < a.length; i++) {
            distance += Math.abs(b[i] - a[i]);
        }
        return distance;
    }
}
