package pl.ksr.extractors;

import pl.ksr.model.Article;
import pl.ksr.model.ExtractedData;

import java.util.List;

public interface Extractor {
    List<ExtractedData> extractFrom(List<Article> articles);
}
