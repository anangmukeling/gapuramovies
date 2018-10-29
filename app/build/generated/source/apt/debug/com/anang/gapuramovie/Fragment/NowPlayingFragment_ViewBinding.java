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

public class NowPlayingFragment_ViewBinding implements Unbinder {
  private NowPlayingFragment target;

  @UiThread
  public NowPlayingFragment_ViewBinding(NowPlayingFragment target, View source) {
    this.target = target;

    target.rvNowPlaying = Utils.findRequiredViewAsType(source, R.id.rv_now_playing, "field 'rvNowPlaying'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.pb_now_playing, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NowPlayingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvNowPlaying = null;
    target.progressBar = null;
  }
}
