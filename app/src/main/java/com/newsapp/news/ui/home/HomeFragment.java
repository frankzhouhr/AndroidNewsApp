package com.newsapp.news.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsapp.news.R;
import com.newsapp.news.databinding.FragmentHomeBinding;
import com.newsapp.news.model.Article;
import com.newsapp.news.repository.NewsRepository;
import com.newsapp.news.repository.NewsViewModelFactory;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter();

        binding.recyclerView.setAdapter(homeNewsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        NewsRepository repository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(HomeViewModel.class);
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("HomeFragment", newsResponse.toString());
                                // setArticles
                                homeNewsAdapter.setArticles(newsResponse.articles);
                                }
                        });
        homeNewsAdapter.setOnClickListener(new HomeNewsAdapter.OnClickListener() {
            @Override
            public void onClick(Article article) {
                // NavHostFragment.findNavController(SaveFragment.this).navigate(R.id.action_title_save_to_detail);
                HomeFragmentDirections.ActionNavigationHomeToNavigationDetail actionTitleSaveToDetail = HomeFragmentDirections.actionNavigationHomeToNavigationDetail();
                actionTitleSaveToDetail.setArticle(article);
                NavHostFragment.findNavController(HomeFragment.this).navigate(actionTitleSaveToDetail);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.onCancel();
    }

}
