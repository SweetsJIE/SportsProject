package com.xjhaobang.sportsproject.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.xjhaobang.sportsproject.R;
import com.xjhaobang.sportsproject.base.BaseFragment;
//import com.xjhaobang.sportsproject.bluetooth.BluetoothChat;
import com.xjhaobang.sportsproject.bluetooth.BluetoothChatService;
import com.xjhaobang.sportsproject.bluetooth.DeviceListActivity;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;

public class FiveFragment extends BaseFragment {


    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_five;
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
}
