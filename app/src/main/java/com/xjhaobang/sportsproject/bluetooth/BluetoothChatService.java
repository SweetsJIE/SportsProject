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
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xjhaobang.sportsproject.fragment.FiveFragment;
import com.xjhaobang.sportsproject.fragment.ThreeFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;



public class BluetoothChatService {

    private static final String TAG = "BluetoothChatService";
    private static final boolean D = true;


    private static final String NAME = "BluetoothChat";


    									 				
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static StringBuffer hexString = new StringBuffer();

    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static boolean bRun = true;

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public BluetoothChatService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
        
    }


    private synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;


        mHandler.obtainMessage(ThreeFragment.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }


    public synchronized int getState() {
        return mState;
    }


    public synchronized void start() {
        if (D) Log.d(TAG, "start");


        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}


        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}


        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }
   

    public synchronized void connect(BluetoothDevice device) {
        if (D) Log.d(TAG, "connect to: " + device);


        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }


        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}


        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }


    @TargetApi(Build.VERSION_CODES.ECLAIR)

	public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (D) Log.d(TAG, "connected");


        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}


        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}


        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}


        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
    


        
        Message msg = mHandler.obtainMessage(ThreeFragment.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(ThreeFragment.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        setState(STATE_CONNECTED);
       }


    public synchronized void stop() {
        if (D) Log.d(TAG, "stop");
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
        setState(STATE_NONE);
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {

        ConnectedThread r;

        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }

        r.write(out);
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        setState(STATE_LISTEN);


        Message msg = mHandler.obtainMessage(ThreeFragment.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(ThreeFragment.TOAST, "无法连接装置");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
        setState(STATE_LISTEN);


        Message msg = mHandler.obtainMessage(ThreeFragment.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(ThreeFragment.TOAST, "装置连接丢失");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }


    private class AcceptThread extends Thread {

        private final BluetoothServerSocket mmServerSocket;

        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public AcceptThread() {
        	
            BluetoothServerSocket tmp = null;


            try {
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public void run() {
            if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            BluetoothSocket socket = null;


            while (mState != STATE_CONNECTED) {
                try {

                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }


                if (socket != null) {
                    synchronized (BluetoothChatService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:

                            connected(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:

                            try {
                                socket.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread");
        }

        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public void cancel() {
            if (D) Log.d(TAG, "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }
    }



    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;


            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
        }

        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");


            mAdapter.cancelDiscovery();


            try {

                mmSocket.connect();
            } catch (IOException e) {
                connectionFailed();

                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }

                BluetoothChatService.this.start();
                return;
            }


            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }


            connected(mmSocket, mmDevice);
        }

        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }


    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        
        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;


            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "没有创建临时sockets", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
           
        }  
       
        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;


            while (true) {
                try {

                    bytes = mmInStream.read(buffer);

                    mHandler.obtainMessage(ThreeFragment.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }
            }
       }
        	 



        public void write(byte[] buffer) {
            try {
      
                mmOutStream.write(buffer);

                mHandler.obtainMessage(ThreeFragment.MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
            
        }
 
        @TargetApi(Build.VERSION_CODES.ECLAIR)
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}
