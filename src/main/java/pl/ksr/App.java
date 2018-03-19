package pl.ksr;

import edu.stanford.nlp.simple.Sentence;

import java.io.IOException;
import java.util.List;

public class App {




    public static void main(String[] args) throws IOException {
//        List<Article> articlesWithPlaces = new ArrayList<Article>();
//        for(int i=0; i<=21; ++i) {
//            articlesWithPlaces.addAll(
//                TestDataService.getData("reut2-"+String.format("%03d", i)+".sgm",
//                                        new String[]{"usa", "argentina"}, LabelType.PLACE));
//    }
//
//        for (Article awp : articlesWithPlaces) {
//            System.out.println(awp);
//        }

        String string = "cars movies training trains eggs ate eaten moving car ";

        for (String val : getLemmasList(string)) {
            System.out.println(val);
        }

    }

    public static List<String> getLemmasList(String text) {
        Sentence sentence = new Sentence(text);
        return sentence.lemmas();
    }

}
