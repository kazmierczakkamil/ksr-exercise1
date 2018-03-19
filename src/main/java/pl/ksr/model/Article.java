package pl.ksr.model;

import pl.ksr.similarityMethods.SimilarityMethod;

public class Article extends ClassifiableData{

    public String articleBody;

    public Article(String label, SimilarityMethod simMethod, String articleBody) {
        super(label,simMethod);
        this.articleBody = articleBody;
    }
}
