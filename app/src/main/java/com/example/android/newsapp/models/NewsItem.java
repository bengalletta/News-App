package com.example.android.newsapp.models;


/**
 * Store information about a news story (author, title, description,... etc) from NewsApi Articles
 */
public class NewsItem {

    private final String title;
    private final String author;
    private final String description;
    private final String publishedAt;
    private String url;
    private String urlToImage;

    private NewsItem(NewsItem.Builder builder) {
        this.author = builder.author;
        this.title = builder.title;
        this.description = builder.description;
        this.url = builder.url;
        this.urlToImage = builder.urlToImage;
        this.publishedAt = builder.publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public static class Builder {
        private final String title;
        private final String author;
        private final String description;
        private final String publishedAt;
        private String url;
        private String urlToImage;

        public Builder(String title, String author, String description, String publishedAt) {
            this.title = title;
            this.author = author;
            this.description = description;
            this.publishedAt = publishedAt;
        }

        public NewsItem build() {
            return new NewsItem(this);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder urlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
            return this;
        }
    }
}

