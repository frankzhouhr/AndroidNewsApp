package com.newsapp.news.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.newsapp.news.R;
import com.newsapp.news.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.HomeNewsViewHolder> {

    interface OnClickListener {
        void onClick(Article article);
    }

    private List<Article> articles = new ArrayList<>();
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public void setArticles(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeNewsAdapter.HomeNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_news_item, parent, false);
        return new HomeNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.title);
        holder.description.setText(article.description);

        holder.itemView.setOnClickListener(v -> {
            onClickListener.onClick(article);
        });

        if (article.url == null) {
            holder.newsImage.setImageResource(R.drawable.ic_empty_image);
        } else {
            Picasso.get().load(article.urlToImage).into(holder.newsImage);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class HomeNewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView newsImage;

        public HomeNewsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);
            newsImage = itemView.findViewById(R.id.news_image);
        }
    }
}
