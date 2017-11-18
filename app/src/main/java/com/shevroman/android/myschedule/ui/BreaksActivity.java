package com.shevroman.android.myschedule.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.shevroman.android.myschedule.BreaksFragmentPagerAdapter;
import com.shevroman.android.myschedule.R;

public class BreaksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaks);
        setTitle("Розклад дзвінків");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        BreaksFragmentPagerAdapter tabsFragmentPagerAdapter = new BreaksFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsFragmentPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
