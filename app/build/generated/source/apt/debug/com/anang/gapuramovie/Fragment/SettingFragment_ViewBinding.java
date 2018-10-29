// Generated code from Butter Knife. Do not modify!
package com.anang.gapuramovie.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import com.anang.gapuramovie.R;
import java.lang.Deprecated;
import java.lang.Override;

public class SettingFragment_ViewBinding implements Unbinder {
  /**
   * @deprecated Use {@link #SettingFragment_ViewBinding(SettingFragment, Context)} for direct creation.
   *     Only present for runtime invocation through {@code ButterKnife.bind()}.
   */
  @Deprecated
  @UiThread
  public SettingFragment_ViewBinding(SettingFragment target, View source) {
    this(target, source.getContext());
  }

  @UiThread
  public SettingFragment_ViewBinding(SettingFragment target, Context context) {
    Resources res = context.getResources();
    target.keyDailyReminder = res.getString(R.string.key_daily_reminder);
    target.keyReleaseReminder = res.getString(R.string.key_release_reminder);
    target.keySettingLanguage = res.getString(R.string.key_setting_language);
  }

  @Override
  @CallSuper
  public void unbind() {
  }
}
