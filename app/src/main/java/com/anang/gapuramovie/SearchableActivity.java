package com.anang.gapuramovie;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.anang.gapuramovie.Adapter.RecyclerNowPlayingAdapter;
import com.anang.gapuramovie.Adapter.RecyclerSearchAdapter;
import com.anang.gapuramovie.Entity.Movies.MovieResponse;
import com.anang.gapuramovie.Entity.Movies.MovieResult;
import com.anang.gapuramovie.Retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends AppCompatActivity {

    private static final String TAG = "SearchableActivity";

    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = BuildConfig.API_KEY;

    private RecyclerSearchAdapter adapter;
    public ProgressDialog mProgress;
    private String search;
    private String searchChange = null;

    private ApiClient apiClient = null;
    private Call<MovieResponse> movieResponseCall;
    private List<MovieResult> movieResults = new ArrayList<>();

    @BindView(R.id.recycler_search_movie)
    RecyclerView recyclerViewSearch;

    @BindView(R.id.search_toolbar)
    Toolbar searchToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Log.d(TAG, "onCreate: started");
        ButterKnife.bind(this);

        handleIntent(getIntent());

        setSupportActionBar(searchToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(search);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage(getString(R.string.progress_loading));
        mProgress.show();

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchableActivity.this));

        if (savedInstanceState != null) {
            movieResults = savedInstanceState.getParcelableArrayList("movies");
            prepareView();
        } else {
            getSearchResult();
        }


//        getSearchResult();

    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", new ArrayList<>(adapter.getMovies()));
    }

    private void prepareView() {
        mProgress.dismiss();
        adapter = new RecyclerSearchAdapter(this, movieResults);
        recyclerViewSearch.setAdapter(adapter);
    }


    private void getSearchResult() {
//        mProgress.show();
        apiClient = ApiClient.getInstance();
        if (searchChange == null) {
            movieResponseCall = apiClient.getApi().getSearchMovies(API_KEY, LANGUAGE, search);
            movieResponseCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        mProgress.dismiss();
                        movieResults = response.body().getMovieResults();
                        if (movieResults != null) {
                            adapter = new RecyclerSearchAdapter(SearchableActivity.this, movieResults);
                            recyclerViewSearch.setAdapter(adapter);
                        }

                        if (movieResults.size() == 0) {
                            Toast.makeText(SearchableActivity.this, R.string.toast_not_found, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(SearchableActivity.this, R.string.toast_failed, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            movieResponseCall = apiClient.getApi().getSearchMovies(API_KEY, LANGUAGE, searchChange);
            movieResponseCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        mProgress.dismiss();
                        movieResults = response.body().getMovieResults();
                        if (movieResults != null) {
                            adapter = new RecyclerSearchAdapter(SearchableActivity.this, movieResults);
                            recyclerViewSearch.setAdapter(adapter);
                        }
                        if (movieResults.size() == 0) {
                            Toast.makeText(SearchableActivity.this, R.string.toast_not_found, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(SearchableActivity.this, R.string.toast_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            search = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchChange = query;
                finish();
                getSearchResult();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgress != null) {
            mProgress.dismiss();
            mProgress = null;
        }
    }

}
