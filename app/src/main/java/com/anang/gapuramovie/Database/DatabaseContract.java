package com.anang.gapuramovie.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Anang Muklhisin on 04/09/2018.
 */

public class DatabaseContract {

    public static String TABLE_FAVOURITE = "favourite";

    public static final class FavouriteColumns implements BaseColumns {

        public static String MOVIE_ID = "movie_id";

        public static String TITLE = "title";

        public static String RELEASE_DATE = "release_date";

        public static String TAGLINE = "tagline";

        public static String VOTE_AVERAGE = "vote_average";

        public static String OVERVIEW = "overview";

        public static String STATUS = "status";

        public static String ORIGINAL_LANGUAGE = "original_language";

        public static String RUNTIME = "runtime";

        public static String HOMEPAGE = "homepage";

        public static String POSTER_URL = "poster_url";

        public static String BACKDROP_URL = "backdrop_url";

    }

    public static final String AUTHORITY = "com.anang.gapuramovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }
}
