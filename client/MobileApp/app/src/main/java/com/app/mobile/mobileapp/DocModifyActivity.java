package com.app.mobile.mobileapp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class DocModifyActivity extends AppCompatActivity {

    private EditText textarea;
    private Handler shareHandler;
    private static String oldOne;
    private static String oldKey;
    private ActionBar actionBar;

    public static String getOldOne() {
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

        if (item.getItemId() == R.id.share) {

            shareHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    final String keyresult = (String) msg.obj;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(DocModifyActivity.this);
                    builder.setTitle("Shared Key");
                    builder.setMessage(keyresult);
                    builder.setPositiveButton("复制", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。
                            ClipData clipData = ClipData.newPlainText(null, keyresult);
                            cm.setPrimaryClip(clipData);
                            Toast.makeText(DocModifyActivity.this, "复制成功", Toast.LENGTH_SHORT).show();

                        }
                    });

                    builder.show();
                }
            };


            new Thread() {
                @Override
                public void run() {
                    String keyresult = Network.setDocAndGetCnKey(getIntent().getStringExtra("doc"));
                    Message msg = shareHandler.obtainMessage();
                    msg.obj = keyresult;
                    shareHandler.sendMessage(msg);
                }
            }.start();
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
                    if (getOldOne().equals("") & Variable.getKey().equals("") & getOldOne().equals(textarea.getText().toString())) { //判断是否是新添加的记录，并且没有添加内容
                        YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                        yunNoteApplication.getHandler().sendEmptyMessage(1001);
                        return new HashMap<>();
                    }

                    if (getOldOne().equals(textarea.getText().toString())) {  //判断是否有修改
                        deleteReply = "1";
                        YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                        yunNoteApplication.cleanList();
                        yunNoteApplication.ListAdd(textarea.getText().toString());
                        yunNoteApplication.ListAdd(Variable.getKey());
                        yunNoteApplication.getHandler().sendEmptyMessage(1000);
                        return new HashMap<>();
                    }

                    deleteReply = Network.deleteDoc(Variable.getKey(), CookieIO.getCookie());

                    if (!deleteReply.equals("1")) {
                        return new HashMap<>();
                        //错误处理，待增加
                    }

                    String newkey = Network.addDocToList(textarea.getText().toString(), CookieIO.getCookie());
                    if (newkey.equals("0")) {
                        return new HashMap<>();
                        //错误处理，待增加
                    }
                    YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                    yunNoteApplication.cleanList();
                    yunNoteApplication.ListAdd(textarea.getText().toString());
                    yunNoteApplication.ListAdd(newkey);
                    yunNoteApplication.getHandler().sendEmptyMessage(1000);
                    return new HashMap<>();

                }

            });

            //关闭线程池
            if (!threadPool.isShutdown()) {
                threadPool.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
