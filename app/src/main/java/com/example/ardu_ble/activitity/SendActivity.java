package com.example.ardu_ble.activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ardu_ble.ConnectedThread;
import com.example.ardu_ble.R;
import com.example.ardu_ble.ReadData;

// input page part

public class SendActivity extends AppCompatActivity implements ReadData{

    private EditText et_val1, et_val2, et_val3, et_val4, et_val5, et_val6, et_val7, et_val8, et_val9, et_val10, et_val11;
    private String st_val1, st_val2, st_val3, st_val4, st_val5, st_val6, st_val7, st_val8, st_val9, st_val10, st_val11;

    private LinearLayout lin_password, lin_val6, lin_val7, lin_val8, lin_val9, lin_val10, lin_val11;

    private Button btn_send, btn_checkPassword;
    private Context mContext;

    private EditText et_pass;
    private String st_pass;
    ConnectedThread connectedThread;

    private boolean isTrue1 = false;
    private boolean isTrue2 = false;
    private boolean isTrue3 = false;
    private boolean isTrue4 = false;
    private boolean isTrue5 = false;
    private boolean isTrue6 = false;
    private boolean isTrue7 = false;
    private boolean isTrue8 = false;
    private boolean isTrue9 = false;
    private boolean isTrue10 = false;
    private boolean isTrue11 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        init_view();
    }

    // sharing all values

    private void init_view(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Input");

        mContext = this;
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_checkPassword = (Button)findViewById(R.id.btn_check_password);

        et_val1 = (EditText)findViewById(R.id.et_val1);
        et_val2 = (EditText)findViewById(R.id.et_val2);
        et_val3 = (EditText)findViewById(R.id.et_val3);
        et_val4 = (EditText)findViewById(R.id.et_val4);
        et_val5 = (EditText)findViewById(R.id.et_val5);
        et_val6 = (EditText)findViewById(R.id.et_val6);
        et_val7 = (EditText)findViewById(R.id.et_val7);
        et_val8 = (EditText)findViewById(R.id.et_val8);
        et_val9 = (EditText)findViewById(R.id.et_val9);
        et_val10 = (EditText)findViewById(R.id.et_val10);
        et_val11 = (EditText)findViewById(R.id.et_val11);

        et_pass = (EditText)findViewById(R.id.et_pass);

        lin_password = (LinearLayout)findViewById(R.id.lin_password);
        lin_val6 = (LinearLayout)findViewById(R.id.lin_val6);
        lin_val7 = (LinearLayout)findViewById(R.id.lin_val7);
        lin_val8 = (LinearLayout)findViewById(R.id.lin_val8);
        lin_val9 = (LinearLayout)findViewById(R.id.lin_val9);
        lin_val10 = (LinearLayout)findViewById(R.id.lin_val10);
        lin_val11 = (LinearLayout)findViewById(R.id.lin_val11);


        lin_val6.setVisibility(View.GONE);
        lin_val7.setVisibility(View.GONE);
        lin_val8.setVisibility(View.GONE);
        lin_val9.setVisibility(View.GONE);
        lin_val10.setVisibility(View.GONE);
        lin_val11.setVisibility(View.GONE);

        btn_send.setVisibility(View.GONE);

        connectedThread = ConnectedThread.getInstance();
        connectedThread.setReadData(this);
        connectedThread.write("C");

        check_changedvalue();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        btn_checkPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_password();
            }
        });

    }

    //==============================================================================================
    // if value is changed, alert will show in screen.
    // this is that part.

    private void check_changedvalue(){

        et_val1.addTextChangedListener(textWatcher);
        et_val2.addTextChangedListener(textWatcher);
        et_val3.addTextChangedListener(textWatcher);
        et_val4.addTextChangedListener(textWatcher);
        et_val5.addTextChangedListener(textWatcher);
        et_val6.addTextChangedListener(textWatcher);
        et_val7.addTextChangedListener(textWatcher);
        et_val8.addTextChangedListener(textWatcher);
        et_val9.addTextChangedListener(textWatcher);
        et_val10.addTextChangedListener(textWatcher);
        et_val11.addTextChangedListener(textWatcher);

    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            Handler mHandler = new Handler(Looper.getMainLooper());

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(s == et_val1.getEditableText() && !s.toString().equals(st_val1)){
                        Alert(1);
                    }else if(s == et_val2.getEditableText() && !s.toString().equals(st_val2)){
                        Alert(2);
                    }else if(s == et_val3.getEditableText() && !s.toString().equals(st_val3)){
                        Alert(3);
                    }else if(s == et_val4.getEditableText() && !s.toString().equals(st_val4)){
                        Alert(4);
                    }else if(s == et_val5.getEditableText() && !s.toString().equals(st_val5)){
                        Alert(5);
                    }else if(s == et_val6.getEditableText() && !s.toString().equals(st_val6)){
                        Alert(6);
                    }else if(s == et_val7.getEditableText() && !s.toString().equals(st_val7)){
                        Alert(7);
                    }else if(s == et_val8.getEditableText() && !s.toString().equals(st_val8)){
                        Alert(8);
                    }else if(s == et_val9.getEditableText() && !s.toString().equals(st_val9)){
                        Alert(9);
                    }else if(s == et_val10.getEditableText() && !s.toString().equals(st_val10)){
                        Alert(10);
                    }else if(s == et_val11.getEditableText() && !s.toString().equals(st_val11)){
                        Alert(11);
                    }

                }
            }, 500);

        }
    };

    public void Alert(int i){

        if(i == 1 && !isTrue1){
            dialog(i);
        }

        if(i == 2 && !isTrue2){
            dialog(i);
        }

        if(i == 3 && !isTrue3){
            dialog(i);
        }

        if(i == 4 && !isTrue4){
            dialog(i);
        }

        if(i == 5 && !isTrue5){
            dialog(i);
        }

        if(i == 6 && !isTrue6){
            dialog(i);
        }

        if(i == 7 && !isTrue7){
            dialog(i);
        }

        if(i == 8 && !isTrue8){
            dialog(i);
        }

        if(i == 9 && !isTrue9){
            dialog(i);
        }

        if(i == 10 && !isTrue10){
            dialog(i);
        }

        if(i == 11 && !isTrue11){
            dialog(i);
        }

    }

    private void dialog(int i){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure change this value?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        switch (i){
                            case 1:
                                st_val1 = et_val1.getText().toString();
                                isTrue1 = true;
                                dialog.cancel();
                                break;
                            case 2:
                                st_val2 = et_val2.getText().toString();
                                isTrue2 = true;
                                dialog.cancel();
                                break;
                            case 3:
                                st_val3 = et_val3.getText().toString();
                                isTrue3 = true;
                                dialog.cancel();
                                break;
                            case 4:
                                st_val4 = et_val4.getText().toString();
                                isTrue4 = true;
                                dialog.cancel();
                                break;
                            case 5:
                                st_val5 = et_val5.getText().toString();
                                isTrue5 = true;
                                dialog.cancel();
                                break;
                            case 6:
                                st_val6 = et_val6.getText().toString();
                                isTrue6 = true;
                                dialog.cancel();
                                break;
                            case 7:
                                st_val7 = et_val7.getText().toString();
                                isTrue7 = true;
                                dialog.cancel();
                                break;
                            case 8:
                                st_val8 = et_val8.getText().toString();
                                isTrue8 = true;
                                dialog.cancel();
                                break;
                            case 9:
                                st_val9 = et_val9.getText().toString();
                                isTrue9 = true;
                                dialog.cancel();
                                break;
                            case 10:
                                st_val10 = et_val10.getText().toString();
                                isTrue10 = true;
                                dialog.cancel();
                                break;
                            case 11:
                                st_val11 = et_val11.getText().toString();
                                isTrue11 = true;
                                dialog.cancel();
                                break;
                        }

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        switch (i){
                            case 1:
                                et_val1.setText(st_val1);
                                dialog.cancel();
                                break;
                            case 2:
                                et_val2.setText(st_val2);
                                dialog.cancel();
                                break;
                            case 3:
                                et_val3.setText(st_val3);
                                dialog.cancel();
                                break;
                            case 4:
                                et_val4.setText(st_val4);
                                dialog.cancel();
                                break;
                            case 5:
                                et_val5.setText(st_val5);
                                dialog.cancel();
                                break;
                            case 6:
                                et_val6.setText(st_val6);
                                dialog.cancel();
                                break;
                            case 7:
                                et_val7.setText(st_val7);
                                dialog.cancel();
                                break;
                            case 8:
                                et_val8.setText(st_val8);
                                dialog.cancel();
                                break;
                            case 9:
                                et_val9.setText(st_val9);
                                dialog.cancel();
                                break;
                            case 10:
                                et_val10.setText(st_val10);
                                dialog.cancel();
                                break;
                            case 11:
                                et_val11.setText(st_val11);
                                dialog.cancel();
                                break;

                        }
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    //==============================================================================================
    //==============================================================================================

    // this is check password part

    private void check_password(){

        st_pass = et_pass.getText().toString();

        if(st_pass.isEmpty()){
            Toast.makeText(this, "Please input your password.", Toast.LENGTH_SHORT).show();
            return;
        }else if(!st_pass.equals("admin")){
            Toast.makeText(this, "Please input correct password.", Toast.LENGTH_SHORT).show();
            return;
        }

        lin_password.setVisibility(View.GONE);

        lin_val6.setVisibility(View.VISIBLE);
        lin_val7.setVisibility(View.VISIBLE);
        lin_val8.setVisibility(View.VISIBLE);
        lin_val9.setVisibility(View.VISIBLE);
        lin_val10.setVisibility(View.VISIBLE);
        lin_val11.setVisibility(View.VISIBLE);

        btn_send.setVisibility(View.VISIBLE);

    }

    // ======================================================================================
    // this is input data to arduino part.

    private void sendData(){

        st_val1 = et_val1.getText().toString() + "$";
        st_val2 = et_val2.getText().toString() + "$";
        st_val3 = et_val3.getText().toString() + "$";
        st_val4 = et_val4.getText().toString() + "$";
        st_val5 = et_val5.getText().toString() + "$";
        st_val6 = et_val6.getText().toString() + "$";
        st_val7 = et_val7.getText().toString() + "$";
        st_val8 = et_val8.getText().toString() + "$";
        st_val9 = et_val9.getText().toString() + "$";
        st_val10 = et_val10.getText().toString() + "$";
        st_val11 = et_val11.getText().toString() + "$";


        if(st_val1.equals("$")){
            Toast.makeText(this, "Please input Max Depletion Time.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val2.equals("$")){
            Toast.makeText(this, "Please input Min Depletion Value.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val3.equals("$")){
            Toast.makeText(this, "Please input Max Aeration Time.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val4.equals("$")){
            Toast.makeText(this, "Please input Max Aeration Level.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val5.equals("$")){
            Toast.makeText(this, "Please input Spot Cleaning Time.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val6.equals("$")){
            Toast.makeText(this, "Please input Settlement Time.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val7.equals("$")){
            Toast.makeText(this, "Please input G.T Cleaning Time.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val8.equals("$")){
            Toast.makeText(this, "Please input Do Resolution Time.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val9.equals("$")){
            Toast.makeText(this, "Please input Max Motor Current.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val10.equals("$")){
            Toast.makeText(this, "Please input F.S Min Value.", Toast.LENGTH_SHORT).show();
            return;
        }else if(st_val11.equals("$")){
            Toast.makeText(this, "Please input Calibration Factor.", Toast.LENGTH_SHORT).show();
            return;
        }

        String total_st = st_val1 + st_val2 + st_val3 + st_val4 + st_val5 + st_val6 + st_val7 + st_val8 + st_val9 + st_val10 + st_val11;

        connectedThread.write(total_st);

        Toast.makeText(this, "input success!", Toast.LENGTH_SHORT).show();

    }

    //=======================================================================================================
    // this is back button part

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

    //====================================================================================================
    // this is the part for get input data from arduino.

    @Override
    public void readtext(String txt) {

        if(txt.isEmpty()){

        }else{
            String[] splits = txt.split("#");

            et_val1.setText(splits[0]);
            et_val2.setText(splits[1]);
            et_val3.setText(splits[2]);
            et_val4.setText(splits[3]);
            et_val5.setText(splits[4]);
            et_val6.setText(splits[5]);
            et_val7.setText(splits[6]);
            et_val8.setText(splits[7]);
            et_val9.setText(splits[8]);
            et_val10.setText(splits[9]);
            et_val11.setText(splits[10]);

            st_val1 = splits[0];
            st_val2 = splits[1];
            st_val3 = splits[2];
            st_val4 = splits[3];
            st_val5 = splits[4];
            st_val6 = splits[5];
            st_val7 = splits[6];
            st_val8 = splits[7];
            st_val9 = splits[8];
            st_val10 = splits[9];
            st_val11 = splits[10];
        }

    }

}