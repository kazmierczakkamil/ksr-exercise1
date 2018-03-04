package pl.ksr;

import pl.ksr.model.ArticleWithPlace;
import pl.ksr.services.FileService;
import pl.ksr.services.TestDataService;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String doc = FileService.readFile("reut2-000.sgm");
        ArticleWithPlace[] articlesWithPlaces =
                TestDataService.getData("reut2-000.sgm", new String[]{"usa", "argentina"});

        for (ArticleWithPlace awp : articlesWithPlaces) {
            System.out.println(awp);
        }
    }
}
