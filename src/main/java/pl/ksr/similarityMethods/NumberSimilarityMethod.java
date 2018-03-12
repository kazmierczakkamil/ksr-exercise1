package pl.ksr.similarityMethods;

import pl.ksr.model.ClassifiableData;
import pl.ksr.model.MyNumber;

public class NumberSimilarityMethod implements SimilarityMethod {
    public double calcSimilarity(ClassifiableData d1, ClassifiableData d2) {
        if (d1 instanceof MyNumber) {
            System.out.println("Calculating similarity for MyNumber objects");
            return 5;
        } else {
            printTypeError();
            return 0;
        }
    }
}
