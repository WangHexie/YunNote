package com.app.mobile.mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DocModifyActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_modify);

        final YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yunNoteApplication.getHandler().sendEmptyMessage(1);
            }
        });


    }
}
