package com.anang.gapuramovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.anang.gapuramovie.Entity.Detail.DetailMovie;
import com.anang.gapuramovie.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.anang.gapuramovie.Database.DatabaseContract.CONTENT_URI;

/**
 * Created by Anang Muklhisin on 05/09/2018.
 */

public class RecyclerFavouriteAdapter extends RecyclerView.Adapter<RecyclerFavouriteAdapter.ViewHolder> {

    private static final String TAG = "RecyclerFavouriteAdapte";
    private final static String IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL;

    private Cursor listFavourite;
    private Context context;

    public RecyclerFavouriteAdapter(Cursor items, Context context) {
        this.context = context;
        setListFavourite(items);
    }

    public void setListFavourite(Cursor listFavourite) {
        this.listFavourite = listFavourite;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called");

        final DetailMovie detailMovie = getItem(position);

        String poster_url = IMAGE_BASE_URL + "w185" + detailMovie.getPosterPath();

        Glide.with(context)
                .load(poster_url)
                .into(holder.imgMoviePoster);

        holder.tvMovieTitle.setText(detailMovie.getTitle());
        holder.tvMovieDescription.setText(detailMovie.getOverview());
        holder.tvMovieDate.setText(dateFormat(detailMovie.getReleaseDate()));
        holder.parentMovieCard.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                String local = "1";
                Intent intent = new Intent(context, DetailMoviesActivity.class);

                Uri uri = Uri.parse(CONTENT_URI + "/" + detailMovie.getId());
                intent.putExtra(DetailMoviesActivity.MOVIE_ID, detailMovie.getId());
                intent.putExtra(DetailMoviesActivity.LOCAL_STATUS, local);
                intent.setData(uri);
                context.startActivity(intent);
            }
        }));

    }

    @Override
    public int getItemCount() {
        if (listFavourite == null) return 0;
        return listFavourite.getCount();
    }

    private DetailMovie getItem(int position) {
        if (!listFavourite.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new DetailMovie(listFavourite);
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

}
