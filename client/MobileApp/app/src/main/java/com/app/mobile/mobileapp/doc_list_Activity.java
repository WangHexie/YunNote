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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

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

import mobileapp.Function.DownloadThread;
import mobileapp.Function.Network;
import mobileapp.Function.StringReformat;


import static android.view.ViewGroup.*;

public class doc_list_Activity extends AppCompatActivity {

    public static final String EXTRA_MESSAGEDOC = "doc";
    public static final String EXTRA_MESSAGEKEY = "key";
    private Handler mhandler;
    private Button test_btn;
    private Handler handler;
    private static Map docAndKey;
    private static int id = 0;
    private boolean modifying = false;

    public static Map getDocAndKey(){
        return docAndKey;
    }

    public static void setDocAndKey(Map docAndKey1){
        docAndKey = docAndKey1;
    }

    private boolean modifying(){
        return modifying;
    }

    private void setModifying(){
        modifying = true;
    }

    private void cancelModifying(){
        modifying = false;
    }

    private static final int MODIFY_COMPLETE = 1000;


    public static void setID(int idToset){
        id = idToset;
    }

    public static int getID(){
        return id;
    }

    public void addPross(){
        ScrollView sv =(ScrollView) findViewById(R.id.scrollView3);
        ProgressBar pb = new ProgressBar(doc_list_Activity.this);
        pb.setId(R.id.progressBar);
        ((ViewGroup)sv.getChildAt(0)).addView(pb,0);
//        View addV = LayoutInflater.from(doc_list_Activity.this).inflate((XmlPullParser) pb,sv,true);
    }

    public void moveProgress(){
        ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar);
        ((ViewGroup) pb.getParent()).removeView(pb);
    }

    public void sendMessage(View view) {
        if (modifying()){
            return;
        }
        setModifying();
        Intent intent = new Intent(this, DocModifyActivity.class);

        View CL = ((ViewGroup)view).getChildAt(0);
        View textV = ((ViewGroup)CL).getChildAt(0);

        TextView realTextV = (TextView) textV;
        String doc = (String)realTextV.getText();
        try{
            doc_list_Activity.setID(realTextV.getId());
        }catch (Exception e){
            Log.d("e",e.toString());
        }
        intent.putExtra(EXTRA_MESSAGEDOC,doc);
        intent.putExtra("key",(String) ((List<String>)getDocAndKey().get("key")).get( doc_list_Activity.getID()));

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {

                if(msg.what == 1000) {
                    Log.d("Error", "啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");

                        YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                        TextView textV = (TextView) findViewById(doc_list_Activity.getID());
                        List<String> docAndKey = yunNoteApplication.getModifyResult();
                        textV.setText(docAndKey.get(0));
                        moveProgress();
                        Log.d("E", docAndKey.get(0));
                        cancelModifying();

                }
            }
        };
        YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
        yunNoteApplication.setHandler(handler);
        startActivity(intent);
        addPross();
    }


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_list_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Handler mHandler = new Handler(){

        @Override
        public void handleMessage (Message msg){

            if (msg.what == 1) {
                moveProgress();
                setDocAndKey((Map)msg.obj);
                Map docAndKey = getDocAndKey();
                List<String> docList = (List<String>)docAndKey.get("doc");
                List<String> keyList = (List<String>)docAndKey.get("key");
                for(int i = 0;i<docList.size();i++){
                    addView(docList.get(i),i);
                }

            }
        }
        };

        DownloadThread dt= new DownloadThread(mHandler);

        dt.start();
        addPross();

//        try {
//                    ExecutorService threadPool = Executors.newCachedThreadPool();
//                    Future<Map> future = threadPool.submit(new Callable<Map>() {
//
//                        @Override
//                        public Map call() throws Exception {
//                            List<String> resultDoc, resultKey;
//                            String jsonString = Network.getList("b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c");
//                            resultDoc = StringReformat.toDocList(jsonString);
//                            resultKey = StringReformat.toKeyList(jsonString);
//                            Map docAndKey = new HashMap();
//                            docAndKey.put("doc",resultDoc);
//                            docAndKey.put("key",resultKey);
//
//                    return docAndKey;
//
//                }
//
//            });
//
//            boolean flag = true;
//            while(flag){
//                //异步任务完成并且未被取消，则获取返回的结果
//                if(future.isDone() && !future.isCancelled()){
//                    this.docAndKey = new HashMap();
//                    this.docAndKey = future.get();
//                    List<String> docList = (List<String>)docAndKey.get("doc");
//                    List<String> keyList = (List<String>)docAndKey.get("key");
//                    for(int i = 0;i<docList.size();i++){
//                        addView(docList.get(i),i);
//                    }
//                    flag = false;
//                }
//            }
//
//            //关闭线程池
//            if(!threadPool.isShutdown()){
//                threadPool.shutdown();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }





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
    }

    private void modifyView(String doc,int index){
        LinearLayout linear=(LinearLayout) findViewById(R.id.linearlay_1);
        View v = linear.getChildAt(index);
        View v3 = ((ViewGroup)v).getChildAt(0);
        View v2 = ((ViewGroup)v3).getChildAt(0);

        TextView x = (TextView) v2;
        x.setId(index);
        x.setText(doc);
    }


}
