package pl.ksr.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.ksr.model.Article;
import pl.ksr.model.LabelType;
import pl.ksr.similarityMethods.SimilarityMethod;

import java.util.ArrayList;
import java.util.List;

// reading data from file
public class TextDataService {

    public static List<Article> getData(String path, String[] labels,
                                        LabelType labelType) {
        List<Article> articles = new ArrayList<>();
        String data = prepareFile(path);

        Document doc = Jsoup.parse(data);
        Elements reuters = doc.select("reuters");

        for (Element reuter : reuters) {
            String label = null;
            String articleBody;

            try {
                if (labelType == LabelType.PLACE)
                    label = reuter.selectFirst("places").text();
                else if (labelType == LabelType.TOPIC)
                    label = reuter.selectFirst("topics").text();
                else if (labelType == LabelType.DATELINE)
                    label = reuter.select("dateline").text();
                articleBody = reuter.selectFirst("article").text();
            } catch (NullPointerException e) {
                continue;
            }

            boolean isLabelCorrect = false;
            for (String labelParam : labels) {
                if (labelParam.equals(label)) {
                    isLabelCorrect = true;
                    break;
                }
            }

            if (isLabelCorrect) {
                Article temp = new Article(label,articleBody);
                articles.add(temp);
            }
        }

        return articles;

    }

    private static String prepareFile(String path) {
        String document = FileService.readFile(path);
        return validateDocument(document);
    }

    private static String validateDocument(String document) {
        return document
                .replace("<BODY>", "<ARTICLE>")
                .replace("</BODY>", "</ARTICLE>");
    }
}


