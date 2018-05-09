package com.app.mobile.mobileapp;

import android.content.Intent;
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
                final String inputKey = keyinput.getText().toString();
                if(!inputKey.equals(""))
                new Thread(){
                    @Override
                    public void run() {
                        String cnkeyResult = Network.getDocByCnKey(inputKey);
                        System.out.print(cnkeyResult);
                        if (!cnkeyResult.equals("0")){
                            Intent intent = new Intent(dockey_inputKeyActivity.this,ShareDocDisplayAcitivity.class);
                            intent.putExtra("doc",cnkeyResult);
                            startActivity(intent);
                        }else {
                            Toast.makeText(dockey_inputKeyActivity.this, "Key无效", LENGTH_LONG).show();
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
