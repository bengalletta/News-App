package com.example.android.newsapp.database;

        import android.provider.BaseColumns;

public class Contract {
    public static class TABLE_ARTICLES implements BaseColumns{
        //name of the table to be used
        public static final String TABLE_NAME = "newsarticles";

        //an article's attributes
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_PUBLISHED_DATE = "published_date";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "article_url";
        public static final String COLUMN_NAME_IMGURL = "image_url";

    }
}