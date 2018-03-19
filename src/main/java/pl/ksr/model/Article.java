package pl.ksr.model;

import pl.ksr.similarityMethods.SimilarityMethod;

public class Article extends ClassifiableData{

    public String articleBody;

    public Article(String label, String articleBody) {
        super(label);
        this.articleBody = articleBody;
    }
}
