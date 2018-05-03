package com.app.mobile.mobileapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mobileapp.Function.Network;

public class DocModifyActivity extends AppCompatActivity {

    private EditText textarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_modify);

        final YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
        textarea = findViewById(R.id.textarea);
        textarea.setText(getIntent().getStringExtra("doc"));
        getIntent().getStringExtra("key");




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences preferences = getSharedPreferences("cookieRW", MODE_PRIVATE);

        YunNoteApplication yunNoteApplication;
        if (Network.deleteDoc(getIntent().getStringExtra("doc") , preferences.getString("cookieRW","0")).equals("1")){
            String newkey = Network.addDocToList(textarea.getText().toString() ,preferences.getString("cookieRW","0") );
            if (!newkey.equals("")){
                yunNoteApplication = (YunNoteApplication) getApplication();
                yunNoteApplication.ListAdd(textarea.getText().toString());
                yunNoteApplication.ListAdd(newkey);
            }
        }


    }
}
