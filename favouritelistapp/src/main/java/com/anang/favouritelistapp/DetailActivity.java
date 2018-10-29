package com.alvin.favouritelistapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alvin.favouritelistapp.database.DatabaseContract;
import com.alvin.favouritelistapp.entity.DetailMovie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alvin.favouritelistapp.database.DatabaseContract.CONTENT_URI;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.BACKDROP_URL;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.HOMEPAGE;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.ORIGINAL_LANGUAGE;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.OVERVIEW;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.POSTER_URL;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.RELEASE_DATE;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.RUNTIME;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.STATUS;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.TAGLINE;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.TITLE;
import static com.alvin.favouritelistapp.database.DatabaseContract.FavouriteColumns.VOTE_AVERAGE;
import static com.alvin.favouritelistapp.database.DatabaseContract.getColumnString;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailActivity";

    private static final String IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL;
    public static String MOVIE_ID = "movie_id";
    private static final String TMDB_URL = "https://www.themoviedb.org/movie/";

    private boolean favourite;
    private int mutedColor = R.attr.colorPrimary;
    private int movieId;
    private String movie_url;

    private ProgressDialog mProgress;
    private Uri uri;
    private DetailMovie detailMovie;

    @BindView(R.id.tb)
    Toolbar tb;

    @BindView(R.id.collapseit)
    CollapsingToolbarLayout ctl;

    @BindView(R.id.img_poster)
    ImageView imgPoster;

    @BindView(R.id.img_backdrop)
    ImageView imgBackdrop;

    @BindView(R.id.detail_movie_title_1)
    TextView movieTitleBig;

    @BindView(R.id.detail_movie_year)
    TextView movieYear;

    @BindView(R.id.detail_movie_tagline)
    TextView movieTagline;

    @BindView(R.id.detail_movie_rate)
    TextView movieRate;

    @BindView(R.id.detail_movie_status)
    TextView movieReleaseStatus;

    @BindView(R.id.detail_movie_date)
    TextView movieReleaseDate;

    @BindView(R.id.detail_movie_language)
    TextView movieLanguage;

    @BindView(R.id.detail_movie_runtime)
    TextView movieRuntime;

    @BindView(R.id.detail_movie_overview)
    TextView movieOverview;

    @BindView(R.id.detail_movie_homepage)
    TextView movieHomepage;

    @BindView(R.id.icon_favorite_unclicked)
    ImageButton icFavoriteUnclicked;

    @BindView(R.id.icon_favorite_clicked)
    ImageButton icFavoriteClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        mProgress = new ProgressDialog(this);

        uri = getIntent().getData();
        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);
        movie_url = TMDB_URL + "" + movieId;

        setSupportActionBar(tb);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        icFavoriteUnclicked.setOnClickListener(this);
        icFavoriteClicked.setOnClickListener(this);

        ctl.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        getMovieSqlite();
    }

    private void getMovieSqlite() {
        Log.d(TAG, "getMovieSqlite: berjalan");

        mProgress.setMessage(getString(R.string.progress_loading));
        mProgress.show();

        Cursor cursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                detailMovie = new DetailMovie(cursor);

                String poster_url = IMAGE_BASE_URL + "w342" + detailMovie.getPosterPath();
                String backdrop_url = IMAGE_BASE_URL + "w780" + detailMovie.getBackdropPath();

                movieTitleBig.setText(detailMovie.getTitle());
                movieYear.setText(detailMovie.getReleaseDate().split("-")[0]);

                //buat set apabila taglinenya kosong
                if (!detailMovie.getTagline().isEmpty()) {
                    movieTagline.setText("\"" + detailMovie.getTagline() + "\"");
                } else {
                    movieTagline.setText(R.string.no_tagline);
                }

                movieRate.setText(detailMovie.getVoteAverage().toString());
                movieReleaseStatus.setText(detailMovie.getStatus());
                movieReleaseDate.setText(dateFormat(detailMovie.getReleaseDate()));
                movieRuntime.setText(detailMovie.getRuntime() + getString(R.string.minute));
                movieOverview.setText(detailMovie.getOverview());
                movieLanguage.setText(detailMovie.getOriginalLanguage());
                movieHomepage.setText(detailMovie.getHomepage());

                Glide.with(getApplicationContext())
                        .load(poster_url)
                        .placeholder(R.drawable.example)
                        .error(R.drawable.example)
                        .crossFade()
                        .into(imgPoster);

                Glide.with(getApplicationContext())
                        .load(backdrop_url)
                        .placeholder(R.drawable.example_backdrop)
                        .error(R.drawable.example_backdrop)
                        .crossFade()
                        .into(imgBackdrop);

                //set title toolbar sesuai judul
                ctl.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));

                // mengubah gambar poster menjadi bitmap
                int myWidth = 600;
                int myHeight = 900;

                Glide.with(getApplicationContext())
                        .load(poster_url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(myWidth, myHeight) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                // mengekstrak warna dari gambar yang digunakan
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                                        ctl.setContentScrimColor(mutedColor);
                                    }
                                });
                            }
                        });
            }
            cursor.close();
        }
        loadSqliteData();
        mProgress.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String dateFormat(String oldDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(oldDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String finalDate = newFormat.format(myDate);

        return finalDate;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.icon_favorite_unclicked) {
            favourite = true;
            setFavoriteIcon();

            ContentValues values = new ContentValues();

            values.put(DatabaseContract.FavouriteColumns.MOVIE_ID, detailMovie.getId());
            values.put(TITLE, detailMovie.getTitle());
            values.put(RELEASE_DATE, detailMovie.getReleaseDate());
            values.put(TAGLINE, detailMovie.getTagline());
            values.put(VOTE_AVERAGE, detailMovie.getVoteAverage());
            values.put(OVERVIEW, detailMovie.getOverview());
            values.put(STATUS, detailMovie.getStatus());
            values.put(ORIGINAL_LANGUAGE, detailMovie.getOriginalLanguage());
            values.put(RUNTIME, detailMovie.getRuntime());
            values.put(HOMEPAGE, detailMovie.getHomepage());
            values.put(POSTER_URL, detailMovie.getPosterPath());
            values.put(BACKDROP_URL, detailMovie.getBackdropPath());

            getContentResolver().insert(CONTENT_URI, values);

            Toast.makeText(this, R.string.add_favourite, Toast.LENGTH_SHORT).show();


        } else if (id == R.id.icon_favorite_clicked) {
            favourite = false;
            setFavoriteIcon();
            getContentResolver().delete(uri,
                    null,
                    null);

            Toast.makeText(this, R.string.remove_favourite, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSqliteData() {
        Cursor cursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String idSqlite = "" + detailMovie.getId();
                String idIntent = "" + movieId;
                if (idSqlite.equals(idIntent)){
                    Log.d(TAG, "loadSqliteData: sama cuy");
                    favourite = true;
                }
            }
            cursor.close();
        } else favourite = false;
        setFavoriteIcon();
    }

    private void setFavoriteIcon() {
        if (favourite) {
            icFavoriteUnclicked.setVisibility(View.INVISIBLE);
            icFavoriteClicked.setVisibility(View.VISIBLE);
        } else {
            icFavoriteUnclicked.setVisibility(View.VISIBLE);
            icFavoriteClicked.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.icon_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String subject = detailMovie.getTitle();
            String description = detailMovie.getOverview();
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            shareIntent.putExtra(Intent.EXTRA_TEXT, subject + "\n\n" + description + "\n\n" + movie_url);
            startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_using)));
        }
        return super.onOptionsItemSelected(item);
    }

}
