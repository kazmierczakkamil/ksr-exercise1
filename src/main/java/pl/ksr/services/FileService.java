package pl.ksr.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class FileService {

    public static String readFile(String path) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(new File(path).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, Charset.forName("UTF-8"));
    }
}
