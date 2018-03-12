package pl.ksr.services;

import pl.ksr.model.MyNumber;
import pl.ksr.similarityMethods.NumberSimilarityMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumbersFileService {
    public List<MyNumber> loadNumbersWithTagsFromFile(String filePath){
        List<MyNumber> numbers = new ArrayList<>();
        String file = FileService.readFile(filePath);
        String[] lines = file.split("\\r?\\n");
        for(String s : lines) {
            String[] ss = s.split(" ");
            numbers.add(new MyNumber(ss[1],new NumberSimilarityMethod(),Double.parseDouble(ss[0])));
        }
        return numbers;
    }
}
