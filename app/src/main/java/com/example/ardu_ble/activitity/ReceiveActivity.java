package com.example.ardu_ble.activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ardu_ble.ConnectedThread;
import com.example.ardu_ble.R;
import com.example.ardu_ble.ReadData;

// output page part

public class ReceiveActivity extends AppCompatActivity implements ReadData {

    private TextView tv_val1, tv_val2, tv_val3, tv_val4, tv_val5, tv_val6, tv_val7, tv_val8, tv_val9;

    ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        init_view();
    }

    // sharing all values

    private void init_view(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("OUTPUT");

        tv_val1 = (TextView)findViewById(R.id.tv_val1);
        tv_val2 = (TextView)findViewById(R.id.tv_val2);
        tv_val3 = (TextView)findViewById(R.id.tv_val3);
        tv_val4 = (TextView)findViewById(R.id.tv_val4);
        tv_val5 = (TextView)findViewById(R.id.tv_val5);
        tv_val6 = (TextView)findViewById(R.id.tv_val6);
        tv_val7 = (TextView)findViewById(R.id.tv_val7);
        tv_val8 = (TextView)findViewById(R.id.tv_val8);
        tv_val9 = (TextView)findViewById(R.id.tv_val9);



        connectedThread = ConnectedThread.getInstance();
        connectedThread.setReadData(this);
        connectedThread.write("A");

    }

    // back button part

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // get output values from arduino

    @Override
    public void readtext(String txt) {

        String[] splits = txt.split("#");

        tv_val1.setText(splits[0]);
        tv_val2.setText(splits[1]);
        tv_val3.setText(splits[2]);
        tv_val4.setText(splits[3]);
        tv_val5.setText(splits[4]);
        tv_val6.setText(splits[5]);
        tv_val7.setText(splits[6]);
        tv_val8.setText(splits[7]);
        tv_val9.setText(splits[8]);

    }
}