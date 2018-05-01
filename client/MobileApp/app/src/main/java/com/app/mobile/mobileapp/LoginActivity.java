package com.app.mobile.mobileapp;

import android.content.Intent;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mobileapp.Function.Login;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(){

                    @Override
                    public void run() {
                        super.run();
                        try {

                            if (!Login.login_check(username.getText().toString(), password.getText().toString()).equals("0")) {

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);

                            } else System.out.println("sb");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();




            }


        });
    }
}
