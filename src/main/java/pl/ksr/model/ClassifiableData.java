package pl.ksr.model;

import pl.ksr.similarityMethods.SimilarityMethod;

public class ClassifiableData {
    public String label;
    SimilarityMethod simMethod;

    ClassifiableData(String label, SimilarityMethod simMethod){
        this.label = label;
        this.simMethod = simMethod;
    }

    double calcSimilarityTo(ClassifiableData data) {
        return simMethod.calcSimilarity(this,data);
    }

    public void setSimilarity(SimilarityMethod simMethod){
        this.simMethod = simMethod;
    }
}
