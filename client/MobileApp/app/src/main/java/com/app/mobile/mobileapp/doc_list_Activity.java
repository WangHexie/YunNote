package com.app.mobile.mobileapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mobileapp.Function.Network;
import mobileapp.Function.StringReformat;

import static android.view.ViewGroup.*;

public class doc_list_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_list_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        addView("中国崛起啦",0);
//        addView("中国人民站起来了,中国人民不死，中国人民永不失败，中华人民共和国万岁，美帝国主义终将倒下人民的决心是永不啦啦啦的，我们呀就要这样行的。狐狸洞里有什么，我们就有什么",3);
//        addView("中国人民站起来了,中国人民不死，中国人民永不失败，中华人民共和国万岁，美帝国主义终将倒下人民的决心是永不啦啦啦的，我们呀就要这样行的。狐狸洞里有什么，我们就有什么",4);
        List<String> futureResult;

        try {
            ExecutorService threadPool = Executors.newCachedThreadPool();
            Future<List<String>> future = threadPool.submit(new Callable<List<String>>() {

                @Override
                public List<String> call() throws Exception {
                    List<String> result;
                    result = StringReformat.toDocList(Network.getList("b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c"));
                    return result;

                }

            });

            boolean flag = true;
            while(flag){
                //异步任务完成并且未被取消，则获取返回的结果
                if(future.isDone() && !future.isCancelled()){
                    futureResult = future.get();
                    for(int i = 0;i<futureResult.size();i++){
                        addView(futureResult.get(i),i);
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
//        CardView card = new CardView(doc_list_Activity.this);
//        LinearLayout.LayoutParams etParam = new LinearLayout.LayoutParams(
//                LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT);
//        etParam.setMargins(24,24,24,16);
//        card.setLayoutParams( etParam);
//        card.setBackgroundColor(getColor(R.id.card_view));
//        TextView text = new TextView(doc_list_Activity.this);
//        text.setText(R.string.loading);
//        card.addView(text);
//        linear.addView(card);

        View addV = LayoutInflater.from(doc_list_Activity.this).inflate(R.layout.card_view,linear,true);
        View v = ((ViewGroup)addV).getChildAt(index);
        View v3 = ((ViewGroup)v).getChildAt(0);
        View v2 = ((ViewGroup)v3).getChildAt(0);

        TextView x = (TextView) v2;
        x.setText(doc);
//        View add2 = LinearLayout.inflate(R.layout.card_view,null);

//        linear.addView(addV);

    }

}
