package com.example.android.newsapp.utils;


import com.example.android.newsapp.models.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by li-en on 6/25/17.
 */

public class NewsJsonUtils {
    private static final String NEWS_ARTICLES = "articles";
    private static String NEWS_AUTHOR = "author";
    private static String NEWS_TITLE = "title";
    private static String NEWS_DESCRIPTION = "description";
    private static String NEWS_URL = "url";
    private static String NEWS_URL_TO_IMAGE = "urlToImage";
    private static String NEWS_PUBLISHED = "publishedAt";

    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException{
        ArrayList<NewsItem> results = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray(NEWS_ARTICLES);

        for(int i = 0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);

            String author = article.getString(NEWS_AUTHOR);
            String title = article.getString(NEWS_TITLE);
            String description = article.getString(NEWS_DESCRIPTION);
            String url = article.getString(NEWS_URL);
            String imgUrl = article.getString(NEWS_URL_TO_IMAGE);
            String published = article.getString(NEWS_PUBLISHED);

            NewsItem newsItem = new NewsItem(author, title, description, url, imgUrl, published);
            results.add(newsItem);
        }


        return results;
    }
}