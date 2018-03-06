package pl.ksr.model;

import lombok.Data;

@Data
public class Article {

    private String place;
    private String topic;
    private String dateline;
    private String articleBody;
}
