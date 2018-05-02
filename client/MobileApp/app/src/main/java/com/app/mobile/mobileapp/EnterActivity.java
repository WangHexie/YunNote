package com.app.mobile.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import mobileapp.Function.CookieCheck;

public class EnterActivity extends AppCompatActivity {
    private static final int GO_LOGIN = 1000;
    private static final int GO_MAIN = 1001;
    private String cookie;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_LOGIN:
                    goLogin();
                    break;
                case GO_MAIN:
                    goMain();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences("cookieRW", MODE_PRIVATE);
        cookie = preferences.getString("cookieRW", "0");
        System.out.println("cookie = " + cookie);
        if (!cookie.equals("0"))
            mhandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
        else {
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    if (!CookieCheck.check(cookie).equals("0")) {
                        mhandler.sendEmptyMessageDelayed(GO_MAIN, 2000);
                    }


                }
            }.start();

        }


    }

    private void goLogin() {
        Intent intent = new Intent(EnterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goMain() {
        Intent intent = new Intent(EnterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}