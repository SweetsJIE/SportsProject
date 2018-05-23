package com.xjhaobang.sportsproject.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xjhaobang.sportsproject.R;
import com.xjhaobang.sportsproject.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by PC on 2018/5/13.
 */

public class FourFragment extends BaseFragment {
    @BindView(R.id.ll_1)
    LinearLayout mLl1;
    @BindView(R.id.ll_2)
    LinearLayout mLl2;
    @BindView(R.id.ll_3)
    LinearLayout mLl3;
    @BindView(R.id.ll_4)
    LinearLayout mLl4;
    @BindView(R.id.ll_5)
    LinearLayout mLl5;
    @BindView(R.id.ll_6)
    LinearLayout mLl6;
    Unbinder unbinder;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }


    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4, R.id.ll_5, R.id.ll_6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_1:
                showToast("该功能尚未开发");
                break;
            case R.id.ll_2:
                showToast("该功能尚未开发");
                break;
            case R.id.ll_3:
                showToast("该功能尚未开发");
                break;
            case R.id.ll_4:
                showToast("该功能尚未开发");
                break;
            case R.id.ll_5:
                showToast("该功能尚未开发");
                break;
            case R.id.ll_6:
                showToast("该功能尚未开发");
                break;
        }
    }
}
