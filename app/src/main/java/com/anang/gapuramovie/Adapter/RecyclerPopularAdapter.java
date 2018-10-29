package com.anang.gapuramovie.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anang.gapuramovie.BuildConfig;
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
 * Created by Anang Muklhisin on 31/07/2018.
 */

public class RecyclerPopularAdapter extends RecyclerView.Adapter<RecyclerPopularAdapter.ViewHolder> {

    private static final String TAG = "RecyclerPopularAdapter";

    private Context mContext;

    private final static String IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL;

    private List<MovieResult> moviesItem;

    public RecyclerPopularAdapter(Context mContext, List<MovieResult> moviesItem) {
        this.mContext = mContext;
        this.moviesItem = moviesItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        String poster_url = IMAGE_BASE_URL + "w185" + moviesItem.get(position).getMoviePosterPath();

        Log.d(TAG, "url image : " + poster_url);

        Glide.with(mContext.getApplicationContext())
                .load(poster_url)
                .error(R.drawable.example)
                .into(holder.imgMoviePoster);

        holder.tvMovieTitle.setText(moviesItem.get(position).getMovieTitle());
        holder.tvMovieDescription.setText(moviesItem.get(position).getMovieDescription());
        holder.tvMovieDate.setText(dateFormat(moviesItem.get(position).getMovieReleaseDate()));
    }

    @Override
    public int getItemCount() {
        return moviesItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String dateFormat(String oldDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(oldDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        String finalDate = newFormat.format(myDate);

        return finalDate;

    }

    public List<MovieResult> getMovies(){
        return  moviesItem;
    }

}
