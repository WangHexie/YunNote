package com.app.mobile.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mobileapp.Function.CookieCheck;
import mobileapp.Function.Login;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login_btn;
    SharedPreferences sharedPreferences;
    Editor editor;
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




        sharedPreferences = getSharedPreferences("cookiesRW", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final String cookie = sharedPreferences.getString("cookies","");
        if(cookie != null)
        new Thread() {
            @Override
            public void run() {
                super.run();
                String cookiecheck_result = CookieCheck.check(cookie);
                if(!cookiecheck_result.equals("0")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("cookiecheck_result",cookiecheck_result);
                    startActivity(intent);
                }
            }


        }.start();




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
                            String loginGetCookie = Login.login_check(username.getText().toString(), password.getText().toString());
                            if (!loginGetCookie.equals("0")) {

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("cookiecheck_result",CookieCheck.check(loginGetCookie));
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
