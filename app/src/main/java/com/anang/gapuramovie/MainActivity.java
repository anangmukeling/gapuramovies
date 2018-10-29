package com.anang.gapuramovie;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.anang.gapuramovie.Fragment.NowPlayingFragment;
import com.anang.gapuramovie.Fragment.PopularFragment;
import com.anang.gapuramovie.Fragment.UpcomingFragment;
import com.anang.gapuramovie.alarm.DailyAlarmReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private ActionBarDrawerToggle toggle;
    private DailyAlarmReceiver dailyAlarmReceiver;

    @BindView(R.id.home_toolbar)
    Toolbar homeToolbar;

    @BindView(R.id.home_view_pager)
    ViewPager homeViewPager;

    @BindView(R.id.home_tab_layout)
    TabLayout homeTabLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        ButterKnife.bind(this);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());

        homeViewPager.setAdapter(adapter);
        homeTabLayout.setupWithViewPager(homeViewPager);

        setSupportActionBar(homeToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        navigationView.setNavigationItemSelectedListener(this);

//        dailyAlarmReceiver = new DailyAlarmReceiver();
//        dailyAlarmReceiver.setRepeatingAlarm(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        // Get the SearchView and set the searchable configuration
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        ComponentName componentName = new ComponentName(getApplicationContext(), SearchableActivity.class);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new PopularFragment();
            }
            if (position == 1) {
                fragment = new NowPlayingFragment();
            }
            if (position == 2) {
                fragment = new UpcomingFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if (position == 0) {
                name = getString(R.string.tab_popular);
            }
            if (position == 1) {
                name = getString(R.string.tab_now_playing);
            }
            if (position == 2) {
                name = getString(R.string.tab_upcoming);
            }
            return name;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(this, drawer, homeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_change_language) {
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
        } else if (id == R.id.nav_favourite) {
            Intent favouriteIntent = new Intent(MainActivity.this, FavouriteActivity.class);
            startActivity(favouriteIntent);
        } else if (id == R.id.nav_setting) {
            Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(settingIntent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
