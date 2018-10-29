package com.anang.gapuramovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.anang.gapuramovie.Entity.Detail.DetailMovie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.BACKDROP_URL;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.HOMEPAGE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.MOVIE_ID;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.ORIGINAL_LANGUAGE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.OVERVIEW;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.POSTER_URL;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.RELEASE_DATE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.RUNTIME;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.STATUS;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.TAGLINE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.TITLE;
import static com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns.VOTE_AVERAGE;
import static com.anang.gapuramovie.Database.DatabaseContract.TABLE_FAVOURITE;

/**
 * Created by Anang Muklhisin on 05/09/2018.
 */

public class FavouriteHelper {

    private static String DATABASE_TABLE = TABLE_FAVOURITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavouriteHelper(Context context) {
        this.context = context;
    }

    public FavouriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<DetailMovie> query() {
        ArrayList<DetailMovie> arrayList = new ArrayList<DetailMovie>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        DetailMovie detailMovie;
        if (cursor.getCount() > 0) {
            do {

                detailMovie = new DetailMovie();
                detailMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                detailMovie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                detailMovie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                detailMovie.setTagline(cursor.getString(cursor.getColumnIndexOrThrow(TAGLINE)));
                detailMovie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                detailMovie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                detailMovie.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(STATUS)));
                detailMovie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
                detailMovie.setRuntime(cursor.getInt(cursor.getColumnIndexOrThrow(RUNTIME)));
                detailMovie.setHomepage(cursor.getString(cursor.getColumnIndexOrThrow(HOMEPAGE)));
                detailMovie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_URL)));
                detailMovie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_URL)));

                arrayList.add(detailMovie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(DetailMovie detailMovie) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(MOVIE_ID, detailMovie.getId());
        initialValues.put(TITLE, detailMovie.getTitle());
        initialValues.put(RELEASE_DATE, detailMovie.getReleaseDate());
        initialValues.put(TAGLINE, detailMovie.getTagline());
        initialValues.put(VOTE_AVERAGE, detailMovie.getVoteAverage());
        initialValues.put(OVERVIEW, detailMovie.getOverview());
        initialValues.put(STATUS, detailMovie.getStatus());
        initialValues.put(ORIGINAL_LANGUAGE, detailMovie.getOriginalLanguage());
        initialValues.put(RUNTIME, detailMovie.getRuntime());
        initialValues.put(HOMEPAGE, detailMovie.getHomepage());
        initialValues.put(POSTER_URL, detailMovie.getPosterPath());
        initialValues.put(BACKDROP_URL, detailMovie.getBackdropPath());

        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(DetailMovie detailMovie) {
        ContentValues args = new ContentValues();

        args.put(MOVIE_ID, detailMovie.getId());
        args.put(TITLE, detailMovie.getTitle());
        args.put(RELEASE_DATE, detailMovie.getReleaseDate());
        args.put(TAGLINE, detailMovie.getTagline());
        args.put(VOTE_AVERAGE, detailMovie.getVoteAverage());
        args.put(OVERVIEW, detailMovie.getOverview());
        args.put(STATUS, detailMovie.getStatus());
        args.put(ORIGINAL_LANGUAGE, detailMovie.getOriginalLanguage());
        args.put(RUNTIME, detailMovie.getRuntime());
        args.put(HOMEPAGE, detailMovie.getHomepage());
        args.put(POSTER_URL, detailMovie.getPosterPath());
        args.put(BACKDROP_URL, detailMovie.getBackdropPath());

        return database.update(DATABASE_TABLE, args, MOVIE_ID + "= '" + detailMovie.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(DATABASE_TABLE, MOVIE_ID + " = '" + id + "'", null);
    }

    public Cursor isFavourite(int id) {
        String movie_id = "" + id;
        return database.query(
                DATABASE_TABLE,
                null,
                MOVIE_ID + " = ?",
                new String[]{movie_id},
                null,
                null,
                null
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , MOVIE_ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, MOVIE_ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, MOVIE_ID + " = ?", new String[]{id});
    }

}
