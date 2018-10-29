package com.anang.gapuramovie.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.anang.gapuramovie.Database.DatabaseContract;
import com.anang.gapuramovie.Database.FavouriteHelper;

import static com.anang.gapuramovie.Database.DatabaseContract.AUTHORITY;
import static com.anang.gapuramovie.Database.DatabaseContract.CONTENT_URI;

/**
 * Created by Anang Muklhisin on 05/09/2018.
 */

public class FavouriteProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        // content://com.dicoding.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVOURITE, MOVIE);

        // content://com.dicoding.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_FAVOURITE+ "/#",
                MOVIE_ID);
    }

    private FavouriteHelper favouriteHelper;

    @Override
    public boolean onCreate() {
        favouriteHelper = new FavouriteHelper(getContext());
        favouriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                cursor = favouriteHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favouriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added ;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = favouriteHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated =  favouriteHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted =  favouriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

}
