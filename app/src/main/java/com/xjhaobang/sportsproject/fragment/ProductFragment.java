package com.xjhaobang.sportsproject.fragment;

import android.os.Bundle;
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

public class ProductFragment extends BaseFragment {
    @BindView(R.id.tab_layout_title)
    android.support.design.widget.TabLayout mTabLayoutTitle;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    Unbinder unbinder;

    private List<Fragment> mFragmentList;
    private List<String> mStringList;
    private EmptyFragment mEmptyFragment1;
    private OneFragment mOneFragment;
    private TwoFragment mTwoFragment;
    private ThreeFragment mThreeFragment;
    private FiveFragment mFiveFragment;
    private FragmentAdapter mFragmentAdapter;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initData(Bundle bundle) {
        mEmptyFragment1 = new EmptyFragment();
        mOneFragment = new OneFragment();
        mTwoFragment = new TwoFragment();
        mThreeFragment = new ThreeFragment();
        mFiveFragment = new FiveFragment();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mEmptyFragment1);
        mFragmentList.add(mOneFragment);
        mFragmentList.add(mTwoFragment);
        mFragmentList.add(mThreeFragment);
        mFragmentList.add(mFiveFragment);

        mStringList = new ArrayList<>();
        mStringList.add("推荐");
        mStringList.add("智能背心");
        mStringList.add("智能导航");
        mStringList.add("智能车锁");
        mStringList.add("添加设备");

        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(0)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(1)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(2)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(3)));
        mTabLayoutTitle.addTab(mTabLayoutTitle.newTab().setText(mStringList.get(4)));
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
