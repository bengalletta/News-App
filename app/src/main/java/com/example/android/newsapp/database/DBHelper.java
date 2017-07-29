package com.example.android.newsapp.database;


        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsarticles.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creates the table on the database
    @Override
    public void onCreate(SQLiteDatabase db){
        String queryString = "CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME + " (" +
                Contract.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_IMGURL + " TEXT" +
                "); ";

        Log.d(TAG, "Creating SQL table: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table " + Contract.TABLE_ARTICLES.TABLE_NAME + " if exists;");
    }

}