// Generated code from Butter Knife. Do not modify!
package com.anang.gapuramovie.Adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.anang.gapuramovie.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecyclerNowPlayingAdapter$ViewHolder_ViewBinding implements Unbinder {
  private RecyclerNowPlayingAdapter.ViewHolder target;

  @UiThread
  public RecyclerNowPlayingAdapter$ViewHolder_ViewBinding(RecyclerNowPlayingAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.parentMovieCard = Utils.findRequiredViewAsType(source, R.id.item_movie_card, "field 'parentMovieCard'", CardView.class);
    target.imgMoviePoster = Utils.findRequiredViewAsType(source, R.id.item_movie_poster, "field 'imgMoviePoster'", ImageView.class);
    target.tvMovieTitle = Utils.findRequiredViewAsType(source, R.id.item_movie_title, "field 'tvMovieTitle'", TextView.class);
    target.tvMovieDescription = Utils.findRequiredViewAsType(source, R.id.item_movie_description, "field 'tvMovieDescription'", TextView.class);
    target.tvMovieDate = Utils.findRequiredViewAsType(source, R.id.item_movie_date, "field 'tvMovieDate'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecyclerNowPlayingAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.parentMovieCard = null;
    target.imgMoviePoster = null;
    target.tvMovieTitle = null;
    target.tvMovieDescription = null;
    target.tvMovieDate = null;
  }
}
