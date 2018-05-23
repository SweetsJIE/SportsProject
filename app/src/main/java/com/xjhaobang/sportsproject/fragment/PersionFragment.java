package com.xjhaobang.sportsproject.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.xjhaobang.sportsproject.R;
import com.xjhaobang.sportsproject.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by PC on 2018/5/13.
 */

public class PersionFragment extends BaseFragment {
    @BindView(R.id.rl_1)
    RelativeLayout mRl1;
    @BindView(R.id.rl_2)
    RelativeLayout mRl2;
    @BindView(R.id.rl_3)
    RelativeLayout mRl3;
    @BindView(R.id.rl_4)
    RelativeLayout mRl4;
    @BindView(R.id.rl_5)
    RelativeLayout mRl5;
    Unbinder unbinder;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_persion;
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

    @OnClick({R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_1:
                showToast("查看纪录功能尚未开发");
                break;
            case R.id.rl_2:
                showToast("骑行计划功能尚未开发");
                break;
            case R.id.rl_3:
                showToast("个人数据库功能尚未开发");
                break;
            case R.id.rl_4:
                showToast("骑行成绩功能尚未开发");
                break;
            case R.id.rl_5:
                showToast("七星团功能尚未开发");
                break;
        }
    }
}
