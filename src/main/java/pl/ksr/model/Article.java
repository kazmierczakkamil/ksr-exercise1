package pl.ksr.model;

public class Article extends ClassifiableData{

    public String articleBody;

    public Article(String label, String articleBody) {
        super(label);
        this.articleBody = articleBody;
    }
}
