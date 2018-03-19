package pl.ksr.model;

import java.util.HashMap;
import java.util.Map;

public class ExtractedData {
    String label;
    public Map<String,Integer> features;

    public ExtractedData(String label){
        this.label = label;
        features = new HashMap<>();
    }
}
