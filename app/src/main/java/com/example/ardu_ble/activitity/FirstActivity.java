package com.example.ardu_ble.activitity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ardu_ble.R;

public class FirstActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_password;

    // This is first Main Screen.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_first);

        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin();
            }
        });
    }

    /* This is firstLogin function in MainActicity
       If you input "1234567" then you will see next searchScreen.
       But you don't input "1234567", then you will see below toast.
    */
    private void getLogin(){
        String st_password = et_password.getText().toString();

        if(st_password.equals("1234567")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show();
        }
    }
}