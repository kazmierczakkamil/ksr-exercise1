package pl.ksr.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleWithPlace {

    private String place;
    private String article;

    @Override
    public String toString() {
        return "PLACE: " + place + "\n"
                + "ARTICLE: " + article + "\n";
    }
}
