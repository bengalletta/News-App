package com.example.android.newsapp.tasks;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

        import com.example.android.newsapp.database.DBHelper;
        import com.example.android.newsapp.database.DatabaseUtils;
        import com.example.android.newsapp.models.NewsItem;
        import com.example.android.newsapp.utils.NetworkUtils;
        import com.example.android.newsapp.utils.NewsJsonUtils;

        import org.json.JSONException;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ArrayList;


public class FetchNews {
    //method to fetch the articles from the API
    public static void fetchArticles(Context context){ //stores them in the database
        ArrayList<NewsItem>  articlesList = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try{
            //clear the database
            DatabaseUtils.deleteAll(db);

            //Grabs results from the API
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            articlesList = NewsJsonUtils.parseJSON(json); //parsing json

            //insert the json results of the articles into the database
            DatabaseUtils.bulkInsert(db, articlesList);

        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}