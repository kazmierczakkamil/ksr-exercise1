package pl.ksr;

import pl.ksr.model.Article;
import pl.ksr.model.LabelType;
import pl.ksr.services.TestDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        List<Article> articlesWithPlaces = new ArrayList<Article>();
        for(int i=0; i<=21; ++i) {
            articlesWithPlaces.addAll(
                TestDataService.getData("reut2-"+String.format("%03d", i)+".sgm",
                                        new String[]{"usa", "argentina"}, LabelType.PLACE));
    }

        for (Article awp : articlesWithPlaces) {
            System.out.println(awp);
        }
    }
}
