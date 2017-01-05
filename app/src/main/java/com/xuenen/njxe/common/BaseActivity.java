package com.xuenen.njxe.common;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.xuenen.njxe.utils.DialogProgressUtil;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        activities.add(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static List<Activity> activities = new LinkedList<>();

    public void exit() {
        for (int i = activities.size() - 1; i >= 0; i--) {
            activities.get(i).finish();
        }
    }

    public void setData(String s) {
    }

}