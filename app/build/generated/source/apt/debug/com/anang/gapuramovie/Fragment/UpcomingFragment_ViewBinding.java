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

public class UpcomingFragment_ViewBinding implements Unbinder {
  private UpcomingFragment target;

  @UiThread
  public UpcomingFragment_ViewBinding(UpcomingFragment target, View source) {
    this.target = target;

    target.rvUpcoming = Utils.findRequiredViewAsType(source, R.id.rv_upcoming, "field 'rvUpcoming'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.pb_upcoming, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UpcomingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvUpcoming = null;
    target.progressBar = null;
  }
}
