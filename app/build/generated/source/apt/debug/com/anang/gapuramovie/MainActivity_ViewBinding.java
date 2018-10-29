// Generated code from Butter Knife. Do not modify!
package com.anang.gapuramovie;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.homeToolbar = Utils.findRequiredViewAsType(source, R.id.home_toolbar, "field 'homeToolbar'", Toolbar.class);
    target.homeViewPager = Utils.findRequiredViewAsType(source, R.id.home_view_pager, "field 'homeViewPager'", ViewPager.class);
    target.homeTabLayout = Utils.findRequiredViewAsType(source, R.id.home_tab_layout, "field 'homeTabLayout'", TabLayout.class);
    target.drawer = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawer'", DrawerLayout.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'navigationView'", NavigationView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.homeToolbar = null;
    target.homeViewPager = null;
    target.homeTabLayout = null;
    target.drawer = null;
    target.navigationView = null;
  }
}
