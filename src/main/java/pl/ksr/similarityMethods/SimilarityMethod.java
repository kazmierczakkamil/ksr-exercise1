package pl.ksr.similarityMethods;

import pl.ksr.model.ClassifiableData;

import javax.management.modelmbean.InvalidTargetObjectTypeException;

public interface SimilarityMethod {

    double calcSimilarity(ClassifiableData d1, ClassifiableData d2);

    default void printTypeError(){
        System.out.println("Cannot compare different data types!");
    }
}