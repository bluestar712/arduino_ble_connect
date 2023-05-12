package com.example.ardu_ble;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// The thread part for bluetooth connect. Don't change it!

public class ConnectedThread extends Thread{

    private BluetoothSocket mmSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;

    private ReadData readData;

    private int readBufferPosition;
    private byte[] readBuffer;

    private static ConnectedThread mActivity;

    public ConnectedThread(){

    }

    public static ConnectedThread getInstance() {
        if (mActivity == null ) mActivity = new ConnectedThread();

        return mActivity;
    }

    public void setParams(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try{
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        }catch (IOException e){

        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        int bytes;

        while (true){
            try{
                bytes = mmInStream.available();
                if(bytes != 0){

                    buffer = new byte[bytes];
                    mmInStream.read(buffer);

                    for(int i = 0; i < bytes; i ++){
                        byte tempByte = buffer[i];

                        if(tempByte == '\n'){
                            byte[] encodedBytes = new byte[readBufferPosition];
                            System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                            final String text = new String(encodedBytes, "US-ASCII");

                            readData.readtext(text);

                            readBufferPosition = 0;
                        }else{
                            readBuffer[readBufferPosition++] = tempByte;
                        }
                    }

                    SystemClock.sleep(100);

                }
            }catch (IOException e){
                e.printStackTrace();

                break;
            }
        }
    }

    public void write(String input){
        byte[] bytes = input.getBytes();
        try{
            mmOutStream.write(bytes);
        }catch (IOException e){

        }
    }

    public void cancel(){
        try{
            mmSocket.close();
        }catch (IOException e){

        }
    }


    public void setReadData(ReadData readData){
        this.readData = readData;
    }

}
