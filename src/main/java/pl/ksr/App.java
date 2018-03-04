package pl.ksr;

import pl.ksr.model.ArticleWithPlace;
import pl.ksr.services.ConverterService;
import pl.ksr.services.FileService;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {

        // todo: need to remove reuters without places or articles

        // todo: read one by one element

        String doc = FileService.readFile("reut2-000.sgm");
        ArticleWithPlace[] articlesWithPlaces = ConverterService.getDocumentInfo(doc);

        for (ArticleWithPlace awp : articlesWithPlaces) {
            System.out.println(awp);
        }


    }
}
