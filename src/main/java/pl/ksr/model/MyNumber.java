package pl.ksr.model;

import pl.ksr.similarityMethods.NumberSimilarityMethod;
import pl.ksr.similarityMethods.SimilarityMethod;

public class MyNumber extends ClassifiableData {
    double data;

    public MyNumber(String label, SimilarityMethod simMethod, double data){
        super(label, simMethod);
        this.data = data;
    }
}
