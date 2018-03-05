package pl.ksr;

import pl.ksr.model.ArticleWithPlace;
import pl.ksr.services.TestDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        List<ArticleWithPlace> articlesWithPlaces = new ArrayList<ArticleWithPlace>();
        for(int i=0; i<=21; ++i) {
            articlesWithPlaces.addAll(
                TestDataService.getData("reut2-"+String.format("%03d", i)+".sgm",
                                        new String[]{"usa", "argentina"}));
    }

        for (ArticleWithPlace awp : articlesWithPlaces) {
            //System.out.println(awp);
        }
    }
}
