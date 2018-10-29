// Generated code from Butter Knife. Do not modify!
package com.alvin.favouritelistapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target, View source) {
    this.target = target;

    target.tb = Utils.findRequiredViewAsType(source, R.id.tb, "field 'tb'", Toolbar.class);
    target.ctl = Utils.findRequiredViewAsType(source, R.id.collapseit, "field 'ctl'", CollapsingToolbarLayout.class);
    target.imgPoster = Utils.findRequiredViewAsType(source, R.id.img_poster, "field 'imgPoster'", ImageView.class);
    target.imgBackdrop = Utils.findRequiredViewAsType(source, R.id.img_backdrop, "field 'imgBackdrop'", ImageView.class);
    target.movieTitleBig = Utils.findRequiredViewAsType(source, R.id.detail_movie_title_1, "field 'movieTitleBig'", TextView.class);
    target.movieYear = Utils.findRequiredViewAsType(source, R.id.detail_movie_year, "field 'movieYear'", TextView.class);
    target.movieTagline = Utils.findRequiredViewAsType(source, R.id.detail_movie_tagline, "field 'movieTagline'", TextView.class);
    target.movieRate = Utils.findRequiredViewAsType(source, R.id.detail_movie_rate, "field 'movieRate'", TextView.class);
    target.movieReleaseStatus = Utils.findRequiredViewAsType(source, R.id.detail_movie_status, "field 'movieReleaseStatus'", TextView.class);
    target.movieReleaseDate = Utils.findRequiredViewAsType(source, R.id.detail_movie_date, "field 'movieReleaseDate'", TextView.class);
    target.movieLanguage = Utils.findRequiredViewAsType(source, R.id.detail_movie_language, "field 'movieLanguage'", TextView.class);
    target.movieRuntime = Utils.findRequiredViewAsType(source, R.id.detail_movie_runtime, "field 'movieRuntime'", TextView.class);
    target.movieOverview = Utils.findRequiredViewAsType(source, R.id.detail_movie_overview, "field 'movieOverview'", TextView.class);
    target.movieHomepage = Utils.findRequiredViewAsType(source, R.id.detail_movie_homepage, "field 'movieHomepage'", TextView.class);
    target.icFavoriteUnclicked = Utils.findRequiredViewAsType(source, R.id.icon_favorite_unclicked, "field 'icFavoriteUnclicked'", ImageButton.class);
    target.icFavoriteClicked = Utils.findRequiredViewAsType(source, R.id.icon_favorite_clicked, "field 'icFavoriteClicked'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tb = null;
    target.ctl = null;
    target.imgPoster = null;
    target.imgBackdrop = null;
    target.movieTitleBig = null;
    target.movieYear = null;
    target.movieTagline = null;
    target.movieRate = null;
    target.movieReleaseStatus = null;
    target.movieReleaseDate = null;
    target.movieLanguage = null;
    target.movieRuntime = null;
    target.movieOverview = null;
    target.movieHomepage = null;
    target.icFavoriteUnclicked = null;
    target.icFavoriteClicked = null;
  }
}
