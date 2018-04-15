package pl.ksr;

import pl.ksr.extractors.Extractor;
import pl.ksr.extractors.NgramExtractor;
import pl.ksr.extractors.TFIDFTextExtractor;
import pl.ksr.extractors.TFTextExtractor;
import pl.ksr.metrics.*;
import pl.ksr.model.Article;
import pl.ksr.model.LabelType;
import pl.ksr.services.TextDataService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    public static List<Article> getData() {
        switch (config.getProperty("source")) {
            case "reuters":
                List<Article> articles = new ArrayList<>();
                LabelType labelType;
                switch (config.getProperty("label")) {
                    case "places":
                        labelType = LabelType.PLACE;
                        break;
                    case "topics":
                        labelType = LabelType.TOPIC;
                        break;
                    default:
                        labelType = LabelType.PLACE;
                }
                String[] labels = config.getProperty("labels").split(",");
                for (int i = 0; i <= 21; ++i) {
                    articles.addAll(
                            TextDataService.getData("data/reut2-" + String.format("%03d", i) + ".sgm",
                                    labels, labelType));
                }
                return articles;
            case "custom":
                return TextDataService.getCustomData("20-ng.txt");
            default:
                return null;
        }
    }

    public static Metric getMetric() {
        switch (config.getProperty("metric")) {
            default:
            case "cosine":
                return new CosDistance();
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
        }
    }

    public static Extractor getExtractor() {
        switch (config.getProperty("extractor")) {
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

    public static int getLimit() { return Integer.parseInt(config.getProperty("limit")); }
}
