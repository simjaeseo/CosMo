package com.example.a21_hg095_java;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SharedPreference;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class BTService extends Service {

    private BluetoothAdapter mAdapter;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ConnectedThread mthread;
    BluetoothSocket mmSocket;

    //수신 관련
    private Handler mBluetoothHandler;
    final static int BT_MESSAGE_READ = 2;
    private StringBuilder recDataString = new StringBuilder();
    private String BTData = null;

    String a;


    private IBinder mBinder = new MyBinder();
    public class MyBinder extends Binder {
        public BTService getService(){
            return BTService.this;
        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void send(String str){
        mthread.write(str);
    }

//    public StringBuilder receive(){
//        return recDataString;
//    }

//    public String receive(){
//        return BTData;
//    }

        public String receive(){
        return a;
    }



    public BTService(){
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mBluetoothHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
//                        recDataString.append(readMessage);
//                        BTData = recDataString.toString();
                        a = readMessage.substring(0,3);
                        SharedPreference.getInstance().createBTState(a);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
//                recDataString.delete(0, recDataString.length());
            }
        };

        mAdapter = BluetoothAdapter.getDefaultAdapter();

        String mac = intent.getStringExtra("macAddress");

        connect(mac);

        return START_STICKY;
    }


//
//    public BTService(Context context)
//    {
////        super();
//        _context = context;
//        mAdapter = BluetoothAdapter.getDefaultAdapter();
//        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
//
//    }


    public void connect(String $address)
    {
        BluetoothDevice device = mAdapter.getRemoteDevice($address);
        connect(device);
    }

    public void connect(BluetoothDevice $device)
    {

//        ConnectThread thread = new ConnectThread($device);
//        thread.start();
        connectDevice($device);
    }


    public void connectDevice(BluetoothDevice device){
            // Use a temporary object that is later assigned to mmSocket, because mmSocket is final
            BluetoothSocket tmp = null;
//         mmDevice = device;
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try
            {
                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_LONG).show();
                tmp = device.createRfcommSocketToServiceRecord(BT_UUID);
                mmSocket = tmp;
                mmSocket.connect();

            }
            catch (IOException e)
            {
                Toast.makeText(getApplicationContext(), "123블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();

            }
            manageConnectedSocket(mmSocket);

        }




//    private class ConnectThread extends Thread
//    {
//        private final BluetoothSocket mmSocket;
//
//
//        public ConnectThread(BluetoothDevice device)
//        {
//            // Use a temporary object that is later assigned to mmSocket, because mmSocket is final
//            BluetoothSocket tmp = null;
////         mmDevice = device;
//            // Get a BluetoothSocket to connect with the given BluetoothDevice
//            try
//            {
//                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_LONG).show();
//                // MY_UUID is the app's UUID string, also used by the server code
//                tmp = device.createRfcommSocketToServiceRecord(BT_UUID);
//            }
//            catch (IOException e)
//            {
//                Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
//            }
//            mmSocket = tmp;
//        }
//
//
//        public void run()
//        {
//            // Cancel discovery because it will slow down the connection
////            mAdapter.cancelDiscovery();
//            try
//            {
//                // Connect the device through the socket. This will block until it succeeds or throws an exception
//                mmSocket.connect();
//            }
//            catch (Exception e1)
//            {
//
//                Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
//            }
//            // Do work to manage the connection (in a separate thread)
//            manageConnectedSocket(mmSocket);
//        }
//
//
//        /** Will cancel an in-progress connection, and close the socket */
//        public void cancel()
//        {
//            try
//            {
//                mmSocket.close();
//            }
//            catch (IOException e)
//            {
//            }
//        }
//    }

    private void manageConnectedSocket(BluetoothSocket $socket)
    {
        mthread = new ConnectedThread($socket);
        mthread.start();

    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[1024]; // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs

            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
//                        Toast.makeText(getApplicationContext(), "발생했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
//                    Toast.makeText(getApplicationContext(), "발생했습니다.", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }


        //데이터 전송을 위한 ConnectedBluetoothThread 스레드의 메서드로 88, 89번째 줄에서 사용
        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }

        //블루투스 소켓을 닫는 메서드입니다. 애플리케이션을 닫으면 어차피 자동으로 닫아지니 여기서 따로 사용할 일은 없겠음
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
//
//    mBluetoothHandler = new Handler(){
//        public void handleMessage(android.os.Message msg){
//            if(msg.what == BT_MESSAGE_READ){
//                String readMessage = null;
//                try {
//                    readMessage = new String((byte[]) msg.obj, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                mTvReceiveData.setText(readMessage);
//            }
//        }
//    };



}