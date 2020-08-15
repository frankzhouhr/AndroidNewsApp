package com.newsapp.news.ui.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsapp.news.R;
import com.newsapp.news.databinding.FragmentNewsDetailBinding;
import com.newsapp.news.model.Article;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends Fragment {
    private FragmentNewsDetailBinding binding;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // optional

        if (getArguments() == null) {
            return;
        }
        Article article = NewsDetailFragmentArgs.fromBundle(getArguments()).getArticle();
        if (article == null) {
            return;
        }
        Log.d("SaveNewsDetailFragment", article.toString());

        binding.title.setText(article.title);
        binding.author.setText(article.author);
        binding.timeStamp.setText(article.publishedAt);
        if (article.urlToImage == null || article.urlToImage.isEmpty()) {
            binding.image.setImageResource(R.drawable.ic_empty_image);
        } else {
            Picasso.get().load(article.urlToImage).into(binding.image);
        }
        binding.description.setText(article.description);
        binding.content.setText(article.content);
    }
}
