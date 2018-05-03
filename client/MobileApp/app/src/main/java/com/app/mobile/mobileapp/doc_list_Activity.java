package com.app.mobile.mobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mobileapp.Function.Network;
import mobileapp.Function.StringReformat;


import static android.view.ViewGroup.*;

public class doc_list_Activity extends AppCompatActivity {

    public static final String EXTRA_MESSAGEDOC = "doc";
    public static final String EXTRA_MESSAGEKEY = "key";
    private Handler mhandler;
    private Button test_btn;
    private Handler handler;
    private Map docAndKey;
    private static int id = 0;

    private static final int MODIFY_COMPLETE = 1000;


    public static void setID(int idToset){
        id = idToset;
    }

    public static int getID(){
        return id;
    }

    final YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();



    public void sendMessage(View view) {
        Intent intent = new Intent(this, DocModifyActivity.class);

        View CL = ((ViewGroup)view).getChildAt(0);
        View textV = ((ViewGroup)CL).getChildAt(0);

        TextView realTextV = (TextView) textV;
        String doc = (String)realTextV.getText();
        doc_list_Activity.setID(realTextV.getId());
        intent.putExtra(EXTRA_MESSAGEDOC,doc);
        intent.putExtra(EXTRA_MESSAGEKEY,((List<String>)this.docAndKey.get("key")).get(doc_list_Activity.getID()));
        intent.putExtra("index",doc_list_Activity.getID());

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case MODIFY_COMPLETE:

                        TextView textV =(TextView) findViewById(doc_list_Activity.getID());
                        List<String> docAndKey = yunNoteApplication.getModifyResult();
                        textV.setText(docAndKey.get(0));

                        break;

                }
            }
        };

        startActivity(intent);
    }


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_list_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
                            ExecutorService threadPool = Executors.newCachedThreadPool();
                    Future<Map> future = threadPool.submit(new Callable<Map>() {

                        @Override
                        public Map call() throws Exception {
                            List<String> resultDoc, resultKey;
                            String jsonString = Network.getList("b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c");
                            resultDoc = StringReformat.toDocList(jsonString);
                            resultKey = StringReformat.toKeyList(jsonString);
                            Map docAndKey = new HashMap();
                            docAndKey.put("doc",resultDoc);
                            docAndKey.put("key",resultKey);

                    return docAndKey;

                }

            });

            boolean flag = true;
            while(flag){
                //异步任务完成并且未被取消，则获取返回的结果
                if(future.isDone() && !future.isCancelled()){
                    this.docAndKey = new HashMap();
                    this.docAndKey = future.get();
                    List<String> docList = (List<String>)docAndKey.get("doc");
                    List<String> keyList = (List<String>)docAndKey.get("key");
                    for(int i = 0;i<docList.size();i++){
                        addView(docList.get(i),i);
                    }
                    flag = false;
                }
            }

            //关闭线程池
            if(!threadPool.isShutdown()){
                threadPool.shutdown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void addView(String doc,int index){
        LinearLayout linear=(LinearLayout) findViewById(R.id.linearlay_1);
        View addV = LayoutInflater.from(doc_list_Activity.this).inflate(R.layout.card_view,linear,true);
        View v = ((ViewGroup)addV).getChildAt(index);
        View v3 = ((ViewGroup)v).getChildAt(0);
        View v2 = ((ViewGroup)v3).getChildAt(0);

        TextView x = (TextView) v2;
        x.setId(index);
        x.setText(doc);
//        View add2 = LinearLayout.inflate(R.layout.card_view,null);

//        linear.addView(addV);

    }


}
