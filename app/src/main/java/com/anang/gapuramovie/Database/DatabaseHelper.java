package com.anang.gapuramovie.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anang.gapuramovie.Database.DatabaseContract.FavouriteColumns;

import static com.anang.gapuramovie.Database.DatabaseContract.TABLE_FAVOURITE;

/**
 * Created by Anang Muklhisin on 05/09/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovieapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVOURITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT)",
            DatabaseContract.TABLE_FAVOURITE,
            FavouriteColumns._ID,
            FavouriteColumns.MOVIE_ID,
            FavouriteColumns.TITLE,
            FavouriteColumns.RELEASE_DATE,
            FavouriteColumns.TAGLINE,
            FavouriteColumns.VOTE_AVERAGE,
            FavouriteColumns.OVERVIEW,
            FavouriteColumns.STATUS,
            FavouriteColumns.ORIGINAL_LANGUAGE,
            FavouriteColumns.RUNTIME,
            FavouriteColumns.HOMEPAGE,
            FavouriteColumns.POSTER_URL,
            FavouriteColumns.BACKDROP_URL
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        onCreate(db);
    }
}
