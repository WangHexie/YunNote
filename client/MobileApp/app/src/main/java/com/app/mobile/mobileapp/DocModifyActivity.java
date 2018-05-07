package com.app.mobile.mobileapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mobileapp.Function.CookieIO;
import mobileapp.Function.Network;
import mobileapp.Function.Variable;

import static android.widget.Toast.LENGTH_LONG;

public class DocModifyActivity extends AppCompatActivity {

    private EditText textarea;

    private static String oldOne;
    private static String oldKey;
    private ActionBar actionBar;

    public static String getOldOne(){
        return oldOne;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_modify);
        final YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
        textarea = findViewById(R.id.textarea);
        Variable.setDoc(getIntent().getStringExtra("doc"));
        Variable.setKey(getIntent().getStringExtra("key"));

        oldOne = Variable.getDoc();

        textarea.setText(Variable.getDoc());
        actionBar = getSupportActionBar();




    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.doc_modify_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.deleteMenuItem) {

            finish();
            try {
                ExecutorService threadPool = Executors.newCachedThreadPool();
                Future<Map> future = threadPool.submit(new Callable<Map>() {
                    @Override
                    public Map call() throws Exception {

                        String deleteReply = Network.deleteDoc(Variable.getKey(), CookieIO.getCookie());
                        if (deleteReply.equals("1")) {
                            YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                            yunNoteApplication.cleanList();
                            yunNoteApplication.getHandler().sendEmptyMessage(1001);
                        }
                        return new HashMap<>();
                    }

                });

                boolean flag = true;
                if (!threadPool.isShutdown()) {
                    threadPool.shutdown();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }




//   //废弃 public void delete(View view){
//        finish();
//        try {
//            ExecutorService threadPool = Executors.newCachedThreadPool();
//            Future<Map> future = threadPool.submit(new Callable<Map>() {
//                @Override
//                public Map call() throws Exception {
//                    String deleteReply = Network.deleteDoc(Variable.getKey(), CookieIO.getCookie());
//                    if (deleteReply.equals("1")) {
//                            YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
//                            yunNoteApplication.cleanList();
//                            yunNoteApplication.getHandler().sendEmptyMessage(1001);
//                    }
//                    return new HashMap<>();
//                }
//
//            });
//
//            boolean flag = true;
//            if (!threadPool.isShutdown()) {
//                threadPool.shutdown();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();


        try {
            ExecutorService threadPool = Executors.newCachedThreadPool();
            Future<Map> future = threadPool.submit(new Callable<Map>() {

                @Override
                public Map call() throws Exception {
                    SharedPreferences preferences = getSharedPreferences("cookieRW", MODE_PRIVATE);
                    String deleteReply;
                    if (getOldOne().equals(textarea.getText().toString())){
                        deleteReply = "1";
                        YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                        yunNoteApplication.cleanList();
                        yunNoteApplication.ListAdd(textarea.getText().toString());
                        yunNoteApplication.ListAdd(Variable.getKey());
                        yunNoteApplication.getHandler().sendEmptyMessage(1000);
                    }else {
                        deleteReply = Network.deleteDoc(Variable.getKey(), CookieIO.getCookie());
                        if (deleteReply.equals("1")) {
                            String newkey = Network.addDocToList(textarea.getText().toString(), CookieIO.getCookie());
                            if (!newkey.equals("0")) {
                                YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                                yunNoteApplication.cleanList();
                                yunNoteApplication.ListAdd(textarea.getText().toString());
                                yunNoteApplication.ListAdd(newkey);
                                yunNoteApplication.getHandler().sendEmptyMessage(1000);
                            }
                        }
                    }


                    return new HashMap<>();

                }

            });

//            boolean flag = true;
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
