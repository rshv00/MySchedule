package com.shevroman.android.myschedule.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shevroman.android.myschedule.breaks.FullLessonsFragment;
import com.shevroman.android.myschedule.breaks.ShortLessonsFragment;

public class BreaksFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    private String tabTitles[] = new String[]{"Повні пари", "Скорочені пари"};
    private Context mContext;

    public BreaksFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FullLessonsFragment();
        } else if (position == 1) {
            return new ShortLessonsFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return tabTitles[0];
            case 1:
                return tabTitles[1];
            default:
                return null;

        }
    }
}