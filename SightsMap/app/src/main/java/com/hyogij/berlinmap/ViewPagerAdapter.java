package com.hyogij.berlinmap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*
 * Description : ViewPagerAdapter class that represents each page as a Fragment
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"MAP", "LIST"};

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MapViewFragment mapViewFragment = new MapViewFragment();
                return mapViewFragment;

            case 1:
                ListViewFragment listViewFragment = new ListViewFragment();
                return listViewFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}