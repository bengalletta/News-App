package com.example.android.newsapp.utils;


import com.example.android.newsapp.models.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Provided methods to handle NewsAPi JSON results
 */
public class NewsApiJsonUtils {
    private static final String TAG = NewsApiJsonUtils.class.getSimpleName();

    private static final String ARTICLES = "articles";

    /**
     * Convert a Stringify JSON result into a List of NewsItem objects
     */
    public static List<NewsItem> parse(String jsonStringResult) throws JSONException {
        JSONObject newsApiJson = new JSONObject(jsonStringResult);
        JSONArray articleJsonArray = newsApiJson.getJSONArray(ARTICLES);
        List<NewsItem> newsItems = new ArrayList<>();

        for (int i = 0; i < articleJsonArray.length(); i++) {
            JSONObject articleJson = articleJsonArray.getJSONObject(i);
            NewsItem newsItem = parse(articleJson);
            newsItems.add(newsItem);
        }
        return newsItems;
    }


    /**
     * Convert an Article JSON object into an NewsItem instance.
     * Retrieve the JSON values for each key and then use those string values
     * to create a NewsItem object
     */
    private static NewsItem parse(JSONObject articleJson) throws JSONException {
        String author = articleJson.getString(Article.AUTHOR);
        String title = articleJson.getString(Article.TITLE);
        String description = articleJson.getString(Article.DESCRIPTION);
        String url = articleJson.getString(Article.URL);
        String urlToImage = articleJson.getString(Article.URL_TO_IMAGE);
        String publishedAt = articleJson.getString(Article.PUBLISHED_AT);

        NewsItem.Builder newsItemBuilder = new NewsItem
            .Builder(title, author, description, publishedAt)
            .url(url)
            .urlToImage(urlToImage);

        return newsItemBuilder.build();
    }


    /**
     * This inner class contains the name of all the fields from News Api Article JSON object
     * that is return from the "articles" field.
     */
    public class Article {

        // Theses fields return a String value
        public static final String AUTHOR = "author";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String URL = "url";
        public static final String URL_TO_IMAGE = "urlToImage";
        public static final String PUBLISHED_AT = "publishedAt";
    }
}
