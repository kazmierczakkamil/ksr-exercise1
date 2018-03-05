package pl.ksr;

import pl.ksr.model.ClassifiableData;
import pl.ksr.model.MyNumber;

import java.lang.reflect.Type;

public class NumberSimilarityMethod {
    double calcSimilarity(ClassifiableData d1, ClassifiableData d2){
        if(d1 instanceof MyNumber) System.out.println("Calculating similarity for MyNumber objects");
        return 0;
    }
}
