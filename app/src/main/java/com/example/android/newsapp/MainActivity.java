package com.example.android.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.newsapp.database.Contract;
import com.example.android.newsapp.database.DBHelper;
import com.example.android.newsapp.database.DatabaseUtils;
import com.example.android.newsapp.tasks.FetchNews;
import com.example.android.newsapp.utils.ScheduleUtilities;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.NewsClickListener{
    static final String TAG = "mainactivity";

    private RecyclerView recyclerView;
    private ProgressBar bar;
    private Cursor cursor;
    private SQLiteDatabase db;
    private NewsAdapter adapter;

    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Checks if app is installed
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = sp.getBoolean("isfirst", true);

        //if not installed it calls to restart the loader
        if(isFirst){
            load();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }

        //Schedules the job dispatcher to refresh for any new articles posted
        ScheduleUtilities.scheduleRefresh(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        db.close();
        cursor.close();
    }

    public Loader<Void> onCreateLoader(int id, final Bundle args){
        return new AsyncTaskLoader<Void>(this) {

            //this is equal to AsyncTask's onPreExecute method
            @Override
            public void onStartLoading(){
                super.onStartLoading();
                bar.setVisibility(View.VISIBLE);
            }

            //Equal to AsyncTask's doInBackGround method
            @Override
            public Void loadInBackground() {
                FetchNews.fetchArticles(MainActivity.this);
                return null;
            }
        };
    }

    //this is equal to AsyncTask's onPostExecute method
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data){
        bar.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        adapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> data){}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_search){
            load();
        }
        return true;
    }

    @Override
    public void onNewsClick(Cursor cursor, int clickedItem){

        //moves the cursor to item that was clicked
        cursor.moveToPosition(clickedItem);

        //get the url link that the user clicked
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("URL: %s", url));

        //opens up the url with user's browser
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void load(){
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }
}