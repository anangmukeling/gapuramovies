package com.anang.gapuramovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.anang.gapuramovie.BuildConfig;
import com.anang.gapuramovie.Entity.Detail.DetailMovie;
import com.anang.gapuramovie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.anang.gapuramovie.Database.DatabaseContract.CONTENT_URI;

/**
 * Created by Anang Muklhisin on 18/09/2018.
 */

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    //    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private int mAppWidgetId;
    private Context mContext;
    private static final String IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL;
    private Cursor cursor;


    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (cursor != null) {
            cursor.close();
        }

        final long identitiyToken = Binder.clearCallingIdentity();

        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);

        Binder.restoreCallingIdentity(identitiyToken);

    }

    @Override
    public void onDestroy() {
        if (cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) return 0;
        else return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(position))
        {
            return null;
        }

        DetailMovie detailMovie = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);

        Bitmap bmp = null;
        String poster_url = IMAGE_BASE_URL + "w342" + detailMovie.getPosterPath();
        String movie_title = detailMovie.getTitle();
        String date = dateFormat(detailMovie.getReleaseDate());
        try {

            bmp = Glide.with(mContext)
                    .load(poster_url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);

        Bundle extras = new Bundle();
        extras.putString(ImageBannerWidget.EXTRA_ITEM, movie_title + "\n" + date);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (cursor.moveToPosition(position)) {
            return cursor.getLong(0);
        } else return position;
    }

    private DetailMovie getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new DetailMovie(cursor);
    }

    @Override
    public boolean hasStableIds() {
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

        SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        String finalDate = newFormat.format(myDate);

        return finalDate;

    }
}
