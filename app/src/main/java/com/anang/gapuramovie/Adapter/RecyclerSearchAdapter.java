package com.anang.gapuramovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anang.gapuramovie.BuildConfig;
import com.anang.gapuramovie.CustomOnItemClickListener;
import com.anang.gapuramovie.DetailMoviesActivity;
import com.anang.gapuramovie.Entity.Movies.MovieResult;
import com.anang.gapuramovie.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anang Muklhisin on 04/08/2018.
 */

public class RecyclerSearchAdapter extends RecyclerView.Adapter<RecyclerSearchAdapter.SearchViewHolder> {

    private static final String TAG = "RecyclerSearchAdapter";

    private final static String IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL;

    private Context mContext;

    private List<MovieResult> searchMovieResults;

    public RecyclerSearchAdapter(Context mContext, List<MovieResult> searchMovieResults) {
        this.mContext = mContext;
        this.searchMovieResults = searchMovieResults;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called");

        String poster_url = IMAGE_BASE_URL + "w185" + searchMovieResults.get(position).getMoviePosterPath();

        Log.d(TAG, "url image : " + poster_url);

        Glide.with(mContext.getApplicationContext())
                .load(poster_url)
                .error(R.drawable.example)
                .into(holder.imgMoviePoster);

        holder.tvMovieTitle.setText(searchMovieResults.get(position).getMovieTitle());
        holder.tvMovieDescription.setText(searchMovieResults.get(position).getMovieDescription());

        if (searchMovieResults.get(position).getMovieReleaseDate() != null) {
            holder.tvMovieDate.setText(dateFormat(searchMovieResults.get(position).getMovieReleaseDate()));
        }

        holder.parentMovieCard.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                showDetailMovie(searchMovieResults.get(position).getMovieId());
            }
        }));

    }

    @Override
    public int getItemCount() {
        return searchMovieResults.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_movie_card)
        CardView parentMovieCard;

        @BindView(R.id.item_movie_poster)
        ImageView imgMoviePoster;

        @BindView(R.id.item_movie_title)
        TextView tvMovieTitle;

        @BindView(R.id.item_movie_description)
        TextView tvMovieDescription;

        @BindView(R.id.item_movie_date)
        TextView tvMovieDate;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String dateFormat(String oldDate) {
        String finalDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(oldDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            finalDate = newFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return finalDate;

    }

    private void showDetailMovie(int movie_id) {
        String local = "0";
        Intent movieIdIntent = new Intent(mContext, DetailMoviesActivity.class);
        movieIdIntent.putExtra(DetailMoviesActivity.MOVIE_ID, movie_id);
        movieIdIntent.putExtra(DetailMoviesActivity.LOCAL_STATUS, local );
        mContext.startActivity(movieIdIntent);
    }

    public List<MovieResult> getMovies(){
        return  searchMovieResults;
    }

}


