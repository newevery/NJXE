package com.xuenen.njxe.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.xuenen.njxe.R;
import com.xuenen.njxe.common.BaseActivity;
import com.xuenen.njxe.ui.adapter.CateAdapter;
import com.xuenen.njxe.ui.adapter.CheckinAdapter;
import com.xuenen.njxe.ui.fragment.CheckinDZJYFragment;
import com.xuenen.njxe.ui.fragment.CheckinSQYYFragment;
import com.xuenen.njxe.ui.fragment.CheckinSWJYFragment;

import java.util.ArrayList;
import java.util.List;

public class CateActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mDatas = new ArrayList<>();

    private CheckinAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate);
        getSupportActionBar().setTitle("借阅记录");
        mTabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        mTitle.add("实物借阅");
        mTitle.add("电子借阅");
        mTitle.add("申请预约");

//        mAdapter = new CheckinAdapter(getSupportFragmentManager(), mTitle);
        mDatas.add(new CheckinSWJYFragment());
        mDatas.add(new CheckinDZJYFragment());
        mDatas.add(new CheckinSQYYFragment());
        CateAdapter adapter=new CateAdapter(getSupportFragmentManager(),mDatas,mTitle);
//        mAdapter.setFragments(mDatas);
//        //设置tabLayout
//        tabLayout.setupWithViewPager(vp);
//        //设置文字的颜色
//        tabLayout.setTabTextColors(Color.GRAY, Color.BLUE);
//        //设置下划线的颜色
//        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);

//        mAdapter = new FragmentAdapter(this, mTitle, mDatas, getSupportFragmentManager());
//
//        //1，设置Tab的标题来自PagerAdapter.getPageTitle()
////        mTabLayout.setTabsFromPagerAdapter(mAdapter);
//        //2，设置TabLayout的选项卡监听
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        //3，设置TabLayout.TabLayoutOnPageChangeListener监听给ViewPager
        /*TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(listener);*/

        //4，viewpager设置适配器
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(0);
        //这个方法是addOnPageChangeListener和setOnTabSelectedListener的封装。代替2,3步骤
        mTabLayout.setupWithViewPager(mViewPager);
//        mAdapter.getCurrentFragment().setData(mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).toString());
    }

}
