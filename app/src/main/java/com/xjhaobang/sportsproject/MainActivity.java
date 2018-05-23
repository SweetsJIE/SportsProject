package com.xjhaobang.sportsproject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.xjhaobang.sportsproject.base.BaseActivity;
import com.xjhaobang.sportsproject.fragment.FourFragment;
import com.xjhaobang.sportsproject.fragment.PersionFragment;
import com.xjhaobang.sportsproject.fragment.ProductFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.iv_scan)
    ImageButton mIvScan;
    @BindView(R.id.fl_main_activity)
    FrameLayout mFlMainActivity;
    @BindView(R.id.ll_main_product)
    LinearLayout mLlMainProduct;
    @BindView(R.id.ll_main_cycle)
    LinearLayout mLlMainCycle;
    @BindView(R.id.ll_main_persion)
    LinearLayout mLlMainPersion;

    private ProductFragment mProductFragment;
    private FourFragment mCycleFragment;
    private PersionFragment mPersionFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mProductFragment == null){
            hideAllFragment(fragmentTransaction);
            mProductFragment = new ProductFragment();
            fragmentTransaction.add(R.id.fl_main_activity,mProductFragment);
            fragmentTransaction.commit();
        }
        mLlMainProduct.setSelected(true);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    private void setSelectedFalse(){
        mLlMainProduct.setSelected(false);
        mLlMainCycle.setSelected(false);
        mLlMainPersion.setSelected(false);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if (mProductFragment != null) fragmentTransaction.hide(mProductFragment);
        if (mCycleFragment != null) fragmentTransaction.hide(mCycleFragment);
        if (mPersionFragment != null) fragmentTransaction.hide(mPersionFragment);
    }

    @OnClick({R.id.iv_scan, R.id.ll_main_product, R.id.ll_main_cycle, R.id.ll_main_persion})
    public void onViewClicked(View view) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.iv_scan:
                break;
            case R.id.ll_main_product:
                hideAllFragment(fragmentTransaction);
                setSelectedFalse();
                mLlMainProduct.setSelected(true);
                if (mProductFragment == null){
                    mProductFragment = new ProductFragment();
                    fragmentTransaction.add(R.id.fl_main_activity,mProductFragment);
                }else {
                    fragmentTransaction.show(mProductFragment);
                }
                break;
            case R.id.ll_main_cycle:
                hideAllFragment(fragmentTransaction);
                setSelectedFalse();
                mLlMainCycle.setSelected(true);
                if (mCycleFragment == null){
                    mCycleFragment = new FourFragment();
                    fragmentTransaction.add(R.id.fl_main_activity,mCycleFragment);
                }else {
                    fragmentTransaction.show(mCycleFragment);
                }
                break;
            case R.id.ll_main_persion:
                hideAllFragment(fragmentTransaction);
                setSelectedFalse();
                mLlMainPersion.setSelected(true);
                if (mPersionFragment == null){
                    mPersionFragment = new PersionFragment();
                    fragmentTransaction.add(R.id.fl_main_activity,mPersionFragment);
                }else {
                    fragmentTransaction.show(mPersionFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }
}
