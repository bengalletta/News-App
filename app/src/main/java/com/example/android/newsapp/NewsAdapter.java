package com.example.android.newsapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newsapp.models.NewsItem;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private static final String TAG = NewsAdapter.class.getSimpleName();

    private List<NewsItem> newsItems;

    private final NewsAdapterOnClickHandler newsAdapterOnClickHandler;

    public NewsAdapter(NewsAdapterOnClickHandler newsAdapterOnClickHandler) {
        this.newsAdapterOnClickHandler = newsAdapterOnClickHandler;
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemLayoutId = R.layout.news_item_list;
        boolean attachToParent = false;

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(itemLayoutId, parent, attachToParent);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsItems == null ? 0 : newsItems.size();
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    public interface NewsAdapterOnClickHandler {
        void onClick(NewsItem newsItem);
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView description;
        private TextView time;


        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            time = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void bind(int position) {
            NewsItem newsItem = newsItems.get(position);
            title.setText(newsItem.getTitle());
            description.setText(newsItem.getDescription());
            time.setText(newsItem.getPublishedAt());
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            NewsItem newsItem = newsItems.get(index);
            newsAdapterOnClickHandler.onClick(newsItem);
        }
    }
}
