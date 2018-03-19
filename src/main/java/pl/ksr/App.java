package pl.ksr;

import pl.ksr.model.Article;
import pl.ksr.model.LabelType;
import pl.ksr.services.TextDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        List<Article> articles = new ArrayList<Article>();

        for(int i=0; i<=21; ++i) {
            articles.addAll(
                TextDataService.getData("reut2-"+String.format("%03d", i)+".sgm",
                                        new String[]{"usa", "argentina"}, LabelType.PLACE, new ));
    }

        for (Article awp : articles) {
            System.out.println(awp);
        }
    }
}
