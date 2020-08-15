package com.newsapp.news.ui.search;

import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.newsapp.news.R;
import com.newsapp.news.databinding.FragmentSearchBinding;
import com.newsapp.news.model.Article;
import com.newsapp.news.repository.NewsRepository;
import com.newsapp.news.repository.NewsViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        newsAdapter.setLikeListener(new SearchNewsAdapter.LikeListener() {
            @Override
            public void onClick(Article article) {
                SearchFragmentDirections.ActionNavigationSearchToNavigationDetail actionNavigationSearchToNavigationDetail = SearchFragmentDirections.actionNavigationSearchToNavigationDetail();
                actionNavigationSearchToNavigationDetail.setArticle(article);
                NavHostFragment.findNavController(SearchFragment.this).navigate(actionNavigationSearchToNavigationDetail);
            }
        });
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(newsAdapter);

        binding.searchView.setOnEditorActionListener(
                (v, actionId, event) -> {
                    String searchText = binding.searchView.getText().toString();
                    if (actionId == EditorInfo.IME_ACTION_DONE && !searchText.isEmpty()) {
                        viewModel.setSearchInput(searchText);
                        return true;
                    } else {
                        return false;
                    }
                });
        NewsRepository repository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SearchViewModel.class);
        viewModel
                .searchNews()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("xydebug", newsResponse.toString());
                                newsAdapter.setArticles(newsResponse.articles);
                            }
                        });
    }

}
