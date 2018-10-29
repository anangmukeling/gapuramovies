package com.alvin.favouritelistapp.entity;

import android.database.Cursor;

import com.alvin.favouritelistapp.database.DatabaseContract;

import static com.alvin.favouritelistapp.database.DatabaseContract.getColumnDouble;
import static com.alvin.favouritelistapp.database.DatabaseContract.getColumnInt;
import static com.alvin.favouritelistapp.database.DatabaseContract.getColumnString;

/**
 * Created by Alvin Tandiardi on 08/09/2018.
 */

public class DetailMovie {

    private String backdropPath;
    private String homepage;
    private Integer id;
    private String originalLanguage;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private Integer runtime;
    private String status;
    private String tagline;
    private String title;
    private Double voteAverage;

    public DetailMovie(String backdropPath, String homepage, Integer id, String originalLanguage, String overview, String posterPath, String releaseDate, Integer runtime,String status, String tagline, String titleDouble, Double voteAverage) {
        this.backdropPath = backdropPath;
        this.homepage = homepage;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    public DetailMovie() {
    }

    public DetailMovie(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.FavouriteColumns.MOVIE_ID);
        this.title = getColumnString(cursor, DatabaseContract.FavouriteColumns.TITLE);
        this.releaseDate = getColumnString(cursor, DatabaseContract.FavouriteColumns.RELEASE_DATE);
        this.tagline = getColumnString(cursor, DatabaseContract.FavouriteColumns.TAGLINE);
        this.voteAverage = getColumnDouble(cursor, DatabaseContract.FavouriteColumns.VOTE_AVERAGE);
        this.overview = getColumnString(cursor, DatabaseContract.FavouriteColumns.OVERVIEW);
        this.status = getColumnString(cursor, DatabaseContract.FavouriteColumns.STATUS);
        this.originalLanguage = getColumnString(cursor, DatabaseContract.FavouriteColumns.ORIGINAL_LANGUAGE);
        this.runtime = getColumnInt(cursor, DatabaseContract.FavouriteColumns.RUNTIME);
        this.homepage = getColumnString(cursor, DatabaseContract.FavouriteColumns.HOMEPAGE);
        this.posterPath = getColumnString(cursor, DatabaseContract.FavouriteColumns.POSTER_URL);
        this.backdropPath = getColumnString(cursor, DatabaseContract.FavouriteColumns.BACKDROP_URL);
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

}
