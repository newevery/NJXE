package com.xuenen.njxe.ui.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.xuenen.njxe.ui.fragment.CheckinSWJYFragment;

import java.util.List;

public class CheckinAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;
private FragmentManager fm;

    public CheckinAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.fm=fm;
        this.titles = titles;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
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