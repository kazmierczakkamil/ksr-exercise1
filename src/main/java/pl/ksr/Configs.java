package pl.ksr;

import pl.ksr.extractors.Extractor;
import pl.ksr.extractors.NgramExtractor;
import pl.ksr.extractors.TFIDFTextExtractor;
import pl.ksr.extractors.TFTextExtractor;
import pl.ksr.metrics.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configs {

    private static Properties config = new Properties();

    static {
        try {
            config.load(Files.newBufferedReader(Paths.get("config.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Metric getMetric() {
        switch(config.getProperty("metric")) {
            case "czebyszew":
                return new CzebyszewMetric();
            case "euclidean":
                return new EuclideanMetric();
            case "jaccard":
                return new JaccardMetric();
            case "manhattan":
                return new ManhattanMetric();
            case "ngram":
                return new NgramMetric(Integer.parseInt(config.getProperty("n")));
            default:
                return new EuclideanMetric();
        }
    }

    public static Extractor getExtractor() {
        switch(config.getProperty("extractor")) {
            case "TF":
                return new TFTextExtractor();
            case "TFIDF":
                return new TFIDFTextExtractor();
            case "ngram":
                return new NgramExtractor(Integer.parseInt(config.getProperty("n")));
            default:
                return new TFTextExtractor();
        }
    }

    public static int getTrainingDataPercentage() {
        return Integer.parseInt(config.getProperty("training"));
    }

    public static int getN() {
        return Integer.parseInt(config.getProperty("n"));
    }

    public static int getK() {
        return Integer.parseInt(config.getProperty("k"));
    }
}
