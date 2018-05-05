package com.app.mobile.mobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mobileapp.Function.Network;
import mobileapp.Function.Variable;

public class DocModifyActivity extends AppCompatActivity {

    private EditText textarea;

    private static String oldOne;
    private static String oldKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_modify);
        final YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
        textarea = findViewById(R.id.textarea);
        Variable.setDoc(getIntent().getStringExtra("doc"));
        Variable.setKey(getIntent().getStringExtra("key"));
        textarea.setText(Variable.getDoc());

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        try {
            ExecutorService threadPool = Executors.newCachedThreadPool();
            Future<Map> future = threadPool.submit(new Callable<Map>() {

                @Override
                public Map call() throws Exception {
                    SharedPreferences preferences = getSharedPreferences("cookieRW", MODE_PRIVATE);
                    String deleteReply = Network.deleteDoc(Variable.getKey(), "b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c");
                    if (deleteReply.equals("1")) {
                        String newkey = Network.addDocToList(textarea.getText().toString(), "b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c");
//            String newkey = Network.addDocToList(textarea.getText().toString() ,preferences.getString("cookieRW","0") );
                        if (!newkey.equals("0")) {
                            YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                            yunNoteApplication.cleanList();
                            yunNoteApplication.ListAdd(textarea.getText().toString());
                            yunNoteApplication.ListAdd(newkey);
                            yunNoteApplication.getHandler().sendEmptyMessage(1000);
                        }
                    }
                    return new HashMap<>();

                }

            });

            boolean flag = true;
//            while(flag){
//                //异步任务完成并且未被取消，则获取返回的结果
//                if(future.isDone() && !future.isCancelled()){
//                    flag = false;
//                    }
//
//                }
            //关闭线程池
            if (!threadPool.isShutdown()) {
                threadPool.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
