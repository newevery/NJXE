package com.xuenen.njxe.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class CateAdapter extends FragmentpaperViewAdapater {
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fm;

    public CateAdapter(FragmentManager fm,List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fm=fm;
        this.fragments=fragments;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
