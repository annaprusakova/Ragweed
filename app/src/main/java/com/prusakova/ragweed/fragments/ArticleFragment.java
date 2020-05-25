package com.prusakova.ragweed.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.prusakova.ragweed.ArticleAdapter;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.ArticleActivity;
import com.prusakova.ragweed.activities.ProfileActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArticleAdapter.RecyclerViewClickListener listener;
    private List<Article> articles;
    private ArticleAdapter articleAdapter;
    private Api apiInterface;
    ProgressBar progressBar;
    private Toolbar toolbar;
    TextView search;
    String[] item;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_article,container,false);
        apiInterface=ApiClient.getClient().create(Api.class);
        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Статті");
        }



        listener = new ArticleAdapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("article_id", articles.get(position).getArticleId());
                intent.putExtra("article_name", articles.get(position).getArticleName());
                intent.putExtra("article_img", articles.get(position).getArticle_img());
                intent.putExtra("article_link", articles.get(position).getArticleLink());
                intent.putExtra("article_text", articles.get(position).getArticle_text());
                startActivity(intent);

            }

        };
         fetchArticle("article","");

        return view;
    }



    public void fetchArticle(String type, String key){

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Article>> call = apiInterface.getArticle(type, key);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                progressBar.setVisibility(View.GONE);
                articles = response.body();
                articleAdapter = new ArticleAdapter(articles,getContext(), listener);
                recyclerView.setAdapter(articleAdapter);
                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchArticle("article", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchArticle("article", newText);
                return false;
            }

        });
    }

}
