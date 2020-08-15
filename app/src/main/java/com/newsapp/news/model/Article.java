package com.newsapp.news.model;

import java.io.Serializable;

public class Article implements Serializable {
    public Source source;
    public String author;
    public String content;
    public String description;
    public String publishedAt;
    public String title;
    public String url;
    public String urlToImage;
}
