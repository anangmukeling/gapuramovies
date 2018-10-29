// Generated code from Butter Knife. Do not modify!
package com.anang.gapuramovie.Fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.anang.gapuramovie.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PopularFragment_ViewBinding implements Unbinder {
  private PopularFragment target;

  @UiThread
  public PopularFragment_ViewBinding(PopularFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_movie, "field 'recyclerView'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress_bar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PopularFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.progressBar = null;
  }
}
