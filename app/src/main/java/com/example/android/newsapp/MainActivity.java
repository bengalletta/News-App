package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.newsapp.models.NewsItem;
import com.example.android.newsapp.utils.NetworkUtils;
import com.example.android.newsapp.utils.NewsApiJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static com.example.android.newsapp.NewsAdapter.NewsAdapterOnClickHandler;

public class MainActivity extends AppCompatActivity implements NewsAdapterOnClickHandler {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NewsAdapterOnClickHandler newsAdapterOnClickHandler = this;

        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_news_api_result);
        newsAdapter = new NewsAdapter(newsAdapterOnClickHandler);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_news);
        LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedId = item.getItemId();

        switch (selectedId) {
            case R.id.item_search:
                URL newsApiUrl = NetworkUtils.buildUrl();
                new FetchNewsTask().execute(newsApiUrl);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(NewsItem newsItem) {
        Uri newsItemWebPage = Uri.parse(newsItem.getUrl());
        Intent intentToStartBrowser = new Intent(Intent.ACTION_VIEW, newsItemWebPage);

        if (intentToStartBrowser.resolveActivity(getPackageManager()) != null) {
            startActivity(intentToStartBrowser);
        }
    }


    private class FetchNewsTask extends AsyncTask<URL, Void, List<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<NewsItem> doInBackground(URL... params) {
            URL url = params[0];
            List<NewsItem> newsItems = null;

            try {
                String stringifyJsonResult = NetworkUtils.getResponseFromHttpUrl(url);
                newsItems = NewsApiJsonUtils.parse(stringifyJsonResult);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return newsItems;
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            super.onPostExecute(newsItems);
            newsAdapter.setNewsItems(newsItems);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
