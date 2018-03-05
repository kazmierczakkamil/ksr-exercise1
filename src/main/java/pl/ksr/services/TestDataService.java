package pl.ksr.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.ksr.model.ArticleWithPlace;

import java.util.ArrayList;
import java.util.List;

// reading data from file
public class TestDataService {

    // getting array of ArticleWithPlace with places in param, path to file
    public static List<ArticleWithPlace> getData(String path, String[] places) {
        List<ArticleWithPlace> articleWithPlaces = new ArrayList<>();
        String data = prepareFile(path);

        Document doc = Jsoup.parse(data);
        Elements reuters = doc.select("reuters");

        for (Element reuter : reuters) {
            String place;
            String article;

            try {
                place = reuter.selectFirst("places").text();
                article = reuter.selectFirst("article").text();
            } catch (NullPointerException e) {
                continue;
            }

            boolean isPlaceCorrect = false;
            for (String placeParam : places) {
                if (placeParam.equals(place)) {
                    isPlaceCorrect = true;
                    break;
                }
            }

            if (isPlaceCorrect)
                articleWithPlaces.add(new ArticleWithPlace(place, article));
        }

        return articleWithPlaces;
    }

    // reading file and changing tags <body> to <article> (tags <body> don't work as other>
    private static String prepareFile(String path) {
        String document = FileService.readFile(path);
        return validateDocument(document);
    }

    // changing tags body to article
    private static String validateDocument(String document) {
        return document
                .replace("<BODY>", "<ARTICLE>")
                .replace("</BODY>", "</ARTICLE>");
    }
}
