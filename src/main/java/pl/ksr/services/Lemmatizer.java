package pl.ksr.services;

import edu.stanford.nlp.simple.Sentence;

import java.util.List;

public class Lemmatizer {
    public static List<String> getLemmasList(String text) {
        return new Sentence(text).lemmas();
    }
}
