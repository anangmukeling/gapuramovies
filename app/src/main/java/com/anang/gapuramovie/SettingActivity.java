package com.anang.gapuramovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.anang.gapuramovie.Fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.setting_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.setting));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
