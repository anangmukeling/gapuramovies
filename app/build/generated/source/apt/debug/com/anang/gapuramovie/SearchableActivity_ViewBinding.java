// Generated code from Butter Knife. Do not modify!
package com.anang.gapuramovie;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchableActivity_ViewBinding implements Unbinder {
  private SearchableActivity target;

  @UiThread
  public SearchableActivity_ViewBinding(SearchableActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchableActivity_ViewBinding(SearchableActivity target, View source) {
    this.target = target;

    target.recyclerViewSearch = Utils.findRequiredViewAsType(source, R.id.recycler_search_movie, "field 'recyclerViewSearch'", RecyclerView.class);
    target.searchToolbar = Utils.findRequiredViewAsType(source, R.id.search_toolbar, "field 'searchToolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchableActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerViewSearch = null;
    target.searchToolbar = null;
  }
}
