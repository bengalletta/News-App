package com.example.android.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.newsapp.database.Contract;
import com.example.android.newsapp.models.NewsItem;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private ArrayList<NewsItem> articlesList;
    NewsClickListener listener;
    private Context context;
    Cursor cursor;

    final static String TAG = "newsadapter";

    public NewsAdapter(Cursor cursor, NewsClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface NewsClickListener{
        void onNewsClick(Cursor cursor, int clickedNewsIndex);
    }

    //Creates the ViewHolders to display on the screen
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        //gets context of activity displayed on the screen atm
        context = viewGroup.getContext();

        //gets teh views from the xml
        LayoutInflater inflater = LayoutInflater.from(context);

        //to make any layout changes
        //if true, it will return to root object and show no changes
        //if false, child views are placed in onCreateViewHolder()
        boolean attachToParentImmediately = false;

        //used to put the layout xml into View objects
        View view = inflater.inflate(R.layout.news_article, viewGroup, attachToParentImmediately);

        NewsHolder holder = new NewsHolder(view);

        return holder;
    }

    //Displays the data of an article at a specified position
    @Override
    public void onBindViewHolder(NewsHolder newsHolder, int position){
        newsHolder.bind(position);
    }

    @Override
    public int getItemCount(){
        //gives number of rows in table
        return cursor.getCount();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImage;
        TextView mTitleText;
        TextView mDescriptionText;
        TextView mTimeText;
        Drawable divider; //remove later

        public NewsHolder(View view){
            super(view);

            //refs id's from news_article.xml
            mImage = (ImageView) view.findViewById(R.id.image);
            mTitleText = (TextView) view.findViewById(R.id.news_title);
            mTimeText = (TextView) view.findViewById(R.id.news_time);
            mDescriptionText = (TextView) view.findViewById(R.id.news_description);

            view.setOnClickListener(this);
        }

        //Gets pos of article and sets view
        public void bind(int position){
            cursor.moveToPosition(position);

            //sets the text of each view
            mTitleText.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            mDescriptionText.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            mTimeText.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE)));

            String imageUrl = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_IMGURL));

            Log.v(TAG, "IMAGE URL: " + imageUrl);

            //applies the image into the ImageView
            if (imageUrl != null){
                Picasso.with(context)
                        .load(imageUrl)
                        .into(mImage);
            }

            Log.v(TAG, "VALUE OF TITLE TEXT IS: " + cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            Log.v(TAG, "VALUE OF DESCRIPTION TEXT IS: " + cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
        }

        @Override
        public void onClick(View view){
            int pos = getAdapterPosition();
            listener.onNewsClick(cursor, pos);
        }
    }
}