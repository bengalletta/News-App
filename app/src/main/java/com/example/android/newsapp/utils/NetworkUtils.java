package com.example.android.newsapp.utils;


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final static String NEWS_APP_BASE_URL = " https://newsapi.org/v1/articles";

    /* Query Parameters */
    private final static String SOURCE_PARAM = "source";
    private final static String SORT_BY_PARAM = "sortBy";
    private final static String API_KEY_PARAM = "apiKey";

    /* Query Values */
    private final static String THE_NEXT_WEB = "the-next-web";
    private final static String LATEST = "latest";
    private final static String API_KEY = "10b497ce5f1e4b2396892fa5cb7d4bc2";


    public static URL buildUrl() {
        Uri uri = Uri.parse(NEWS_APP_BASE_URL).buildUpon()
            .appendQueryParameter(SOURCE_PARAM, THE_NEXT_WEB)
            .appendQueryParameter(SORT_BY_PARAM, LATEST)
            .appendQueryParameter(API_KEY_PARAM, API_KEY)
            .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
