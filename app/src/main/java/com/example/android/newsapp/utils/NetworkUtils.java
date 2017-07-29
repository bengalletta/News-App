package com.example.android.newsapp.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;



public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String STATIC_NEWS_URL = "https://newsapi.org/v1/articles?";
    private static final String NEWS_BASE_URL = STATIC_NEWS_URL;

    private final static String SOURCE = "the-next-web";
    /* The order the API is to return */
    private final static String SORT_BY = "latest";
    //My API key from news.org
    private final static String API_KEY = "22e2c2e2f27f4664929e84e91e3f9d9a";


    final static String SOURCE_PARAM = "source";
    final static String SORT_PARAM = "sortBy";
    final static String API_KEY_PARAM = "apiKey";

    //pass as a parameter if needed
    public static String get_API_Key(){ return API_KEY; }

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, SOURCE)
                .appendQueryParameter(SORT_PARAM, SORT_BY)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
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