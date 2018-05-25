package com.xjhaobang.sportsproject.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xjhaobang.sportsproject.R;
import com.xjhaobang.sportsproject.adapter.FragmentAdapter;
import com.xjhaobang.sportsproject.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by PC on 2018/5/13.
 */

public class CycleFragment extends BaseFragment {
    @BindView(R.id.tab_layout_title)
    TabLayout mTabLayoutTitle;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    Unbinder unbinder;

    private List<Fragment> mFragmentList;
    private List<String> mStringList;
    private FourFragment mFourFragment;
    private EmptyFragment mEmptyFragment1,mEmptyFragment2,mEmptyFragment3,mEmptyFragment4,mEmptyFragment5,mEmptyFragment6;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_cycle;
    }

    @Override
    protected void initData(Bundle bundle) {
        mFourFragment = new FourFragment();
        mEmptyFragment1 = new EmptyFragment();
        mEmptyFragment2 = new EmptyFragment();
        mEmptyFragment3 = new EmptyFragment();
        mEmptyFragment4 = new EmptyFragment();
        mEmptyFragment5 = new EmptyFragment();
        mEmptyFragment6 = new EmptyFragment();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mFourFragment);
        mFragmentList.add(mEmptyFragment1);
        mFragmentList.add(mEmptyFragment2);
        mFragmentList.add(mEmptyFragment3);
        mFragmentList.add(mEmptyFragment4);
        mFragmentList.add(mEmptyFragment5);
        mFragmentList.add(mEmptyFragment6);

        mStringList = new ArrayList<>();
        mStringList.add("推荐");
        mStringList.add("打卡");
        mStringList.add("分享");
        mStringList.add("活动");
        mStringList.add("话题");
        mStringList.add("赛事");
        mStringList.add("交友");

        mTabLayoutTitle.setTabMode(TabLayout.MODE_FIXED);
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(0)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(1)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(2)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(3)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(4)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(5)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(6)));
        mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(),mFragmentList,mStringList);
        mViewpager.setAdapter(mFragmentAdapter);
        mTabLayoutTitle.setupWithViewPager(mViewpager);
        mViewpager.setCurrentItem(1);
        mTabLayoutTitle.getTabAt(1).select();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

}
