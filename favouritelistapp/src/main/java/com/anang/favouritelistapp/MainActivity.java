package com.alvin.favouritelistapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.alvin.favouritelistapp.adapter.RecyclerFavouriteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alvin.favouritelistapp.database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private RecyclerFavouriteAdapter adapter;
    private Cursor list;

    @BindView(R.id.favourite_toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_favourite)
    RecyclerView recyclerView;

    @BindView(R.id.pb_favourite)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.favourite));

        adapter = new RecyclerFavouriteAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new LoadFavouriteAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadFavouriteAsync().execute();
    }

    private class LoadFavouriteAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor items) {
            super.onPostExecute(items);
            progressBar.setVisibility(View.GONE);

            list = items;
            adapter.setListFavourite(list);

            if (list.getCount() == 0) {
                showSnackbarMessage(getString(R.string.no_favourite_data));
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.language_icon) {
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
