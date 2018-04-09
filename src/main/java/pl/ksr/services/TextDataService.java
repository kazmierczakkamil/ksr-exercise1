package pl.ksr.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.ksr.model.Article;
import pl.ksr.model.LabelType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static List<Article> getCustomData(String path) {
        Set<Article> articles = new HashSet<>();
        String data = FileService.readFile(path);
        String lines[] = data.split("\\r?\\n");

        for (String line : lines) {
            String[] labelAndBody = line.split("\\t+");
            articles.add(new Article(labelAndBody[0], labelAndBody[1]));
        }

        return new ArrayList<>(articles);
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


