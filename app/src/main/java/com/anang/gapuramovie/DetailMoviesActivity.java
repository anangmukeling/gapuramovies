package com.anang.gapuramovie;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.anang.gapuramovie.Adapter.RecyclerNowPlayingAdapter;
import com.anang.gapuramovie.Database.DatabaseContract;
import com.anang.gapuramovie.Retrofit.ApiClient;
import com.anang.gapuramovie.Entity.Detail.DetailMovie;
import com.anang.gapuramovie.widget.ImageBannerWidget;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.anang.gapuramovie.Database.DatabaseContract.CONTENT_URI;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.BACKDROP_URL;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.HOMEPAGE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.ORIGINAL_LANGUAGE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.OVERVIEW;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.POSTER_URL;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.RELEASE_DATE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.RUNTIME;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.STATUS;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.TAGLINE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.TITLE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.VOTE_AVERAGE;
import static com.anang.gapuramovie.Database.DatabaseContract.getColumnString;

public class DetailMoviesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailMoviesActivity";

    private static final String IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL;
    private static final String API_KEY = BuildConfig.API_KEY;
    public static String MOVIE_ID = "movie_id";
    public static String LOCAL_STATUS = "local_status";
    private static final String TMDB_URL = "https://www.themoviedb.org/movie/";

    private int movie_id;
    private int mutedColor = R.attr.colorPrimary;
    private boolean favourite;
    private String movie_url;
    private String check;

    private ProgressDialog mProgress;
    private ApiClient apiClient = null;
    private Call<DetailMovie> detailMovieCall;

    private DetailMovie detailMovie;
    private Uri uri;

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
        setContentView(R.layout.activity_detail_movies);
        ButterKnife.bind(this);

        mProgress = new ProgressDialog(this);

        uri = getIntent().getData();

        setSupportActionBar(tb);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie_id = getIntent().getIntExtra(MOVIE_ID, movie_id);
        check = getIntent().getStringExtra(LOCAL_STATUS);
        movie_url = TMDB_URL + "" + movie_id;

        if (savedInstanceState != null) {
            detailMovie = savedInstanceState.getParcelable("movies");
            checkAgain();
        } else {
            checkStatus();
        }

        icFavoriteUnclicked.setOnClickListener(this);
        icFavoriteClicked.setOnClickListener(this);

        ctl.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        loadSqliteData();
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movies", detailMovie);
    }

    private void checkStatus() {
        if (check.equals("1")) {
            Log.d(TAG, "onCreate: sama cuy");
            getMovieSqlite();
        }
        if (check.equals("0")) {
            Log.d(TAG, "onCreate: gak sama cuy");
            getDetailMovie();
        }
    }

    private void checkAgain(){
        if (check.equals("1")) {
            Log.d(TAG, "onCreate: sama cuy");
            getMovieSqlite();
        }
        if (check.equals("0")) {
            Log.d(TAG, "onCreate: gak sama cuy");
            setMovie();
        }
    }

    private void getDetailMovie() {
        apiClient = ApiClient.getInstance();
        mProgress.setMessage(getString(R.string.progress_loading));
        mProgress.show();
        mProgress.setCanceledOnTouchOutside(false);
        detailMovieCall = apiClient.getApi().getDetailMovies(movie_id, API_KEY);
        detailMovieCall.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {

                if (response.isSuccessful()) {
                    detailMovie = response.body();

                    mProgress.dismiss();

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
                    ctl.setTitle(detailMovie.getTitle());

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
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
                mProgress.dismiss();
                Toast.makeText(DetailMoviesActivity.this, R.string.toast_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMovie(){
        mProgress.dismiss();

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
        ctl.setTitle(detailMovie.getTitle());

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

            values.put(MOVIE_ID, detailMovie.getId());
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

            sendUpdateFavoriteList(this);

            Toast.makeText(this, R.string.add_favourite, Toast.LENGTH_SHORT).show();


        } else if (id == R.id.icon_favorite_clicked) {
            favourite = false;
            setFavoriteIcon();

            getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movie_id),
                    null,
                    null);

            sendUpdateFavoriteList(this);

            Toast.makeText(this, R.string.remove_favourite, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSqliteData() {
        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + movie_id),
                null,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String idIntent = "" + movie_id;
                String movieId = getColumnString(cursor, DatabaseContract.FavouriteColumns.MOVIE_ID);
                if (movieId.equals(idIntent)) favourite = true;
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

    public static void sendUpdateFavoriteList(Context context)
    {
        Intent i = new Intent(context, ImageBannerWidget.class);
        i.setAction(ImageBannerWidget.UPDATE_WIDGET);
        context.sendBroadcast(i);
    }

}
