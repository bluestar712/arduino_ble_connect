package com.example.ardu_ble.activitity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ardu_ble.ConnectedThread;
import com.example.ardu_ble.R;
import com.example.ardu_ble.SecActivity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/*
    This is Main Control screen.
    There are device scan button, input page button, output page button, and curve page button.

 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_sendActivity, btn_receiveActivity, btn_plotActivity; // declare input page, receive page, curve page button
    private TextView textStatus; // declare connectted status

    private ImageButton btn_search; // declare device scan button
    private ListView listView; // declare scanned bluetooth devices list
    private RelativeLayout rel_background; // declare background photo

    private final static int REQUEST_ENABLE_BT = 1; // declare bluetooth connect request

    BluetoothAdapter btAdapter;
    BluetoothSocket btSocket;
    ConnectedThread connectedThread;

    String[] permission_list = {  // declare permission
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    Set<BluetoothDevice> pairedDevices; // declare pairedDevices
    ArrayAdapter<String> btArrayAdapter; // declare bluetooth device name adapter
    ArrayList<String> deviceAddressArray; // declare device address

    String TAG = "MainActivity";
    UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_view();

    }

    // sharing all values
    private void init_view(){

        ActivityCompat.requestPermissions(MainActivity.this, permission_list,  1);

        btn_sendActivity = (Button)findViewById(R.id.SendActivity);
        btn_receiveActivity = (Button)findViewById(R.id.ReceiveActivity);
        btn_plotActivity = (Button)findViewById(R.id.PlotActivity);
        rel_background = (RelativeLayout)findViewById(R.id.rel_background);

        textStatus = (TextView) findViewById(R.id.text_status);
        btn_search = (ImageButton) findViewById(R.id.btn_search);

        btn_sendActivity.setEnabled(false);
        btn_receiveActivity.setEnabled(false);
        btn_plotActivity.setEnabled(false);

        listView = (ListView) findViewById(R.id.listview);

        btn_plotActivity.setOnClickListener(this);
        btn_receiveActivity.setOnClickListener(this);
        btn_sendActivity.setOnClickListener(this);
        btn_search.setOnClickListener(this);


        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!btAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        listView.setAdapter(btArrayAdapter);

        listView.setOnItemClickListener(new myOnItemClickListener());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SendActivity:
                Intent i_send = new Intent(this, SendActivity.class);
                startActivity(i_send);
                break;
            case R.id.ReceiveActivity:
                Intent i_receive = new Intent(this, ReceiveActivity.class);
                startActivity(i_receive);
                break;
            case R.id.PlotActivity:
                Intent i_plot = new Intent(this, PlotActivity.class);
                startActivity(i_plot);
                break;
            case R.id.btn_search:
                getSearchDevices();
                break;

        }
    }

    // paired devices function
    private void getPairedDevices(){

        btArrayAdapter.clear();
        if(deviceAddressArray != null && !deviceAddressArray.isEmpty()){
            deviceAddressArray.clear();
        }

        pairedDevices = btAdapter.getBondedDevices();

        if(pairedDevices.size() > 0){

            for(BluetoothDevice device : pairedDevices){
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
            }
        }
    }

    //To get searched bluetooth device list function
    private void getSearchDevices(){

        rel_background.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        if(btAdapter.isDiscovering()){
            btAdapter.cancelDiscovery();
        }else{
            if(btAdapter.isEnabled()){
                btAdapter.startDiscovery();
                btArrayAdapter.clear();

                if(deviceAddressArray != null && !deviceAddressArray.isEmpty()){
                    deviceAddressArray.clear();
                }
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(receiver, filter);
            }else{
                Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // bluetooth broadcastreceiver part
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)){

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                if(deviceName == null){
                    deviceName = "";
                }
                String deviceHardwareAddress = device.getAddress();
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
                btArrayAdapter.notifyDataSetChanged();

            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    // if you click one list item of searched bluetooth device list, then this function will work.

    public class myOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), btArrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            textStatus.setText("try...");

            final String name = btArrayAdapter.getItem(position);
            final String address = deviceAddressArray.get(position);
            boolean flag = true;

            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            try{
                btSocket = createBluetoothSocket(device);
                btSocket.connect();
            }catch (IOException e){
                flag = false;
                textStatus.setText("Connection failed!");
                e.printStackTrace();
            }

            if(flag){
                textStatus.setText("Connected to " + name);
                connectedThread = ConnectedThread.getInstance();
                connectedThread.setParams(btSocket);
                connectedThread.start();

                btn_sendActivity.setEnabled(true);
                btn_receiveActivity.setEnabled(true);
                btn_plotActivity.setEnabled(true);

                listView.setVisibility(View.GONE);
                rel_background.setVisibility(View.VISIBLE);


            }
        }
    }


    // create bluetooth socket part
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException{
        try{
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket)m.invoke(device, BT_MODULE_UUID);
        }catch (Exception e){
            Log.e(TAG, "Could not create Insecure RFComm Connection", e);
        }

        return device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }


}