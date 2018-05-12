package com.app.mobile.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import mobileapp.Function.CookieIO;
import mobileapp.Function.Network;

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {

    private final static int LOGIN_FAILED = 1000;
    private final static int LOGIN_SUCCESS = 1001;

    private EditText username;
    private EditText password;
    private Button login_btn;
    private Button sign_btn;
    private boolean modifying = false;
    private ProgressBar loginprocess;

    private boolean modifying() {
        return modifying;
    }

    private void setModifying() {
        modifying = true;
    }

    private void cancelModifying() {
        modifying = false;
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_FAILED:
                    loginprocess.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "登陆失败", LENGTH_LONG).show();
                    cancelModifying();

                    break;
                case LOGIN_SUCCESS:

                    loginprocess.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, doc_list_Activity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }

        ;
    };


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

        loginprocess = findViewById(R.id.loginprocess);
        loginprocess.setVisibility(View.INVISIBLE);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);

        sign_btn = findViewById(R.id.sign_btn);

        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifying()) {
                    return;
                }
                setModifying();
                loginprocess.setVisibility(View.VISIBLE);

                new Thread() {

                    @Override
                    public void run() {
                        super.run();
                        try {
                            String loginGetCookie = Network.login_check(username.getText().toString(), password.getText().toString());
                            if (!loginGetCookie.equals("0")) {
                                SharedPreferences preferences = getSharedPreferences("cookieRW", MODE_PRIVATE);
                                Editor editor = preferences.edit();
                                editor.putString("cookieRW", loginGetCookie);
                                CookieIO.setCookie(loginGetCookie);
                                editor.commit();
                                CookieIO.setResponse(Network.check(loginGetCookie));
                                mhandler.sendEmptyMessage(LOGIN_SUCCESS);

                            } else {
                                mhandler.sendEmptyMessage(LOGIN_FAILED);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }


        });
    }


}
