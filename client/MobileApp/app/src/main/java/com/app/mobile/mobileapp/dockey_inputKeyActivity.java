package com.app.mobile.mobileapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mobileapp.Function.Network;

import static android.widget.Toast.LENGTH_LONG;

public class dockey_inputKeyActivity extends AppCompatActivity {

    private EditText keyinput;
    private Button submit;
    private Handler handler;
    private String cnkeyResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dockey_input_key);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        submit = findViewById(R.id.key_submit);


        keyinput = findViewById(R.id.keyInput);
        submit.setOnClickListener(new View.OnClickListener(){




            @Override
            public void onClick(View view) {

                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 1){
                            Intent intent = new Intent(dockey_inputKeyActivity.this,ShareDocDisplayAcitivity.class);
                            intent.putExtra("doc",cnkeyResult);
                            startActivity(intent);
                        }
                        if (msg.what == 0){
                            Toast.makeText(dockey_inputKeyActivity.this, "Key无效", LENGTH_LONG).show();
                        }
                    }
                };


                final String inputKey = keyinput.getText().toString();
                if(!inputKey.equals(""))
                new Thread(){
                    @Override
                    public void run() {
                        cnkeyResult = Network.getDocByCnKey(inputKey);
                        if (!cnkeyResult.equals("0")){
                            handler.sendEmptyMessage(1);
                        }else {
                            handler.sendEmptyMessage(0);
                        }

                    }
                }.start();
                else {
                    Toast.makeText(dockey_inputKeyActivity.this, "不能为空", LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(dockey_inputKeyActivity.this,doc_list_Activity.class);
                startActivity(intent);
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
