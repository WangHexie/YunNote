package com.app.mobile.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mobileapp.Function.CookieIO;
import mobileapp.Function.Network;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText pass_confirm;
    private Button btn;
    private final static int SIGN_SUCCESS = 1000;
    private final static int SIGN_FAILED = 1001;
    private boolean modifying = false;

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
                case SIGN_SUCCESS:
                    Toast.makeText(RegisterActivity.this, "OK", LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this , doc_list_Activity.class);
                    startActivity(intent);
                    finish();
                    break;
                case SIGN_FAILED:
                    Toast.makeText(RegisterActivity.this, "注册失败", LENGTH_LONG).show();
                    cancelModifying();
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        pass_confirm = findViewById(R.id.pass_confirm);
        btn = findViewById(R.id.sub_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modifying()){
                    return;
                }
                setModifying();
                if (!username.getText().toString().equals("") && password.getText().toString().equals(pass_confirm.getText().toString())){

                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            System.out.println("进来了");
                            String nameExistCheckResult = Network.ifNameExist(username.getText().toString());
                            System.out.println(nameExistCheckResult);
                            if (nameExistCheckResult.equals("1"))
                                mhandler.sendEmptyMessage(SIGN_FAILED);
                            else if (nameExistCheckResult.equals("0")){
                                String signResult = Network.signIn(username.getText().toString() , password.getText().toString());
                                System.out.println(signResult);
                                if (!signResult.equals("0")){
                                    SharedPreferences preferences = getSharedPreferences("cookieRW", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("cookieRW",signResult);
                                    CookieIO.setCookie(signResult);
                                    CookieIO.setResponse("0");
                                    editor.commit();
                                    mhandler.sendEmptyMessage(SIGN_SUCCESS);
                                }

                            }


                        }
                    }.start();
                }
            }
        });
    }
}
