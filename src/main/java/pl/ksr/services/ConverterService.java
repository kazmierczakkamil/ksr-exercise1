package pl.ksr.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.ksr.model.ArticleWithPlace;

public class ConverterService {

    public static String validateDocument(String document) {
        return document
                .replace("<BODY>", "<ARTICLE>")
                .replace("</BODY>", "</ARTICLE>");
    }

    public static ArticleWithPlace[] getDocumentInfo(String document) {
        String validDocument = validateDocument(document);
        Document doc = Jsoup.parse(validDocument);
        Elements places = doc.select("places");
        Elements articles = doc.select("article");
        int size = articles.size();

        if (size == 0)
            throw new IllegalArgumentException();

        ArticleWithPlace[] articleWithPlaces = new ArticleWithPlace[size];

        for (int i = 0; i < articleWithPlaces.length; i++) {
            articleWithPlaces[i] = new ArticleWithPlace(places.get(i).text(), articles.get(i).text());
        }

        return articleWithPlaces;
    }
}
