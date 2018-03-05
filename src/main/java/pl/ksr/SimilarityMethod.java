package pl.ksr;

import pl.ksr.model.ClassifiableData;

public interface SimilarityMethod {
    double calcSimilarity(ClassifiableData d1, ClassifiableData d2);
}