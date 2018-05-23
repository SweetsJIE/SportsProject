/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xjhaobang.sportsproject.bluetooth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xjhaobang.sportsproject.R;

import java.util.Set;

/**
 * �����ƺ���һ���Ի������г����κ���Ե��豸��װ��
 *���ֳ����ֺ�ķ��֡���һ���豸�����û�ѡ��
 *��ַ���豸���͸��ҳ����
 *�����ͼ��
 */
public class DeviceListActivity extends Activity
{
	// ����
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;

	// ���ر����ͼ
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	// ������
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// ָ��������ʽ
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);

		// ���ȡ������û�����
		setResult(Activity.RESULT_CANCELED);

		// ���ȡ������û�����
		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});

		// ��ʼ��������������һ�������װ�ú�

         //һ���·��ֵ��豸
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		//Ѱ�Һͽ�������豸�б�
		ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Ѱ�Һͽ���Ϊ�·��ֵ��豸�б�
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// ע��ʱ���͹㲥���豸
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// �㲥ʱ���������ע��
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// ��ȡ��������������
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// �õ�һ��Ŀǰ����豸
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		// If there are paired devices, add each one to the ArrayAdapter
		if (pairedDevices.size() > 0)
		{
			findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
			for (BluetoothDevice device : pairedDevices)
			{
				mPairedDevicesArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress());
			}
		}
		else
		{
			String noDevices = getResources().getText(R.string.none_paired)
					.toString();
			mPairedDevicesArrayAdapter.add(noDevices);
		}
	}

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		// ȷ������û�з�����
		if (mBtAdapter != null)
		{
			mBtAdapter.cancelDiscovery();
		}

		// ע���㲥����
		this.unregisterReceiver(mReceiver);
	}

	/**
	 * ������bluetoothadapter����װ��
	 */
	@TargetApi(Build.VERSION_CODES.ECLAIR)
	private void doDiscovery()
	{
		if (D) Log.d(TAG, "doDiscovery()");

		// ��ʾɨ��ĳƺ�
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);

		// �����豸����Ļ
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		// ��������Ѿ����֣���ֹ��
		if (mBtAdapter.isDiscovering())
		{
			mBtAdapter.cancelDiscovery();
		}

		// Ҫ���bluetoothadapter����
		mBtAdapter.startDiscovery();
	}

	// ������ڵ������豸��listviews
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener()
	{
		@TargetApi(Build.VERSION_CODES.ECLAIR)
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
		{
			// ��Ϊ�����˷ѵģ�ȡ���������ǵ�����
			mBtAdapter.cancelDiscovery();

			// ����豸��ַ�����ǽ�17�ֵ�
			//��ͼ
			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);

			//���������ͼ�Ͱ�����ַ
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

			//������������
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

	// ��broadcastreceiver�����豸��
	// �仯�ı���ʱ���������
	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@TargetApi(Build.VERSION_CODES.ECLAIR)
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();

			// �������豸
			if (BluetoothDevice.ACTION_FOUND.equals(action))
			{
				//�������豸�������ͼ
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// ������Ѿ���ԣ�����������Ϊ��������
				// ����
				if (device.getBondState() != BluetoothDevice.BOND_BONDED)
				{
					mNewDevicesArrayAdapter.add(device.getName() + "\n"
							+ device.getAddress());
				}
				//�����ֺ󣬸ı�����
			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			{
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.select_device);
				if (mNewDevicesArrayAdapter.getCount() == 0)
				{
					String noDevices = getResources().getText(
							R.string.none_found).toString();
					mNewDevicesArrayAdapter.add(noDevices);
				}
			}
		}
	};

}