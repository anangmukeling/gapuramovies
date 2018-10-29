// Generated code from Butter Knife. Do not modify!
package com.anang.gapuramovie;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavouriteActivity_ViewBinding implements Unbinder {
  private FavouriteActivity target;

  @UiThread
  public FavouriteActivity_ViewBinding(FavouriteActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FavouriteActivity_ViewBinding(FavouriteActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.favourite_toolbar, "field 'toolbar'", Toolbar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.rv_favourite, "field 'recyclerView'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.pb_favourite, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavouriteActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recyclerView = null;
    target.progressBar = null;
  }
}
