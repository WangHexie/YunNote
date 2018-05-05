package com.app.mobile.mobileapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobileapp.Function.CookieIO;
import mobileapp.Function.DownloadThread;
import mobileapp.Function.StringReformat;

public class doc_list_Activity extends AppCompatActivity {

    public static final String EXTRA_MESSAGEDOC = "doc";
    public static final String EXTRA_MESSAGEKEY = "key";
    private Handler mhandler;
    private Button test_btn;
    private Handler handler;
    private static Map docAndKey;
    private static int id = 0;

    private static final int MODIFY_COMPLETE = 1000;
    private boolean modifying = false;

    public static void addKey(String key, int x) {
        List<String> keyList = (List<String>) getDocAndKey().get("key");
        keyList.add(x, key);
    }

    public static void changeXthKey(String key, int x) {
        List<String> keyList = (List<String>) getDocAndKey().get("key");
        keyList.remove(x);
        keyList.add(x, key);
    }

    public static Map getDocAndKey() {
        return docAndKey;
    }

    public static void setDocAndKey(Map docAndKey1) {
        docAndKey = docAndKey1;
    }

    private boolean modifying() {
        return modifying;
    }

    private void setModifying() {
        modifying = true;
    }

    private void cancelModifying() {
        modifying = false;
    }

    public static void setID(int idToset) {
        id = idToset;
    }

    public static int getID() {
        return id;
    }

    public void addProgress() {
        ScrollView sv = (ScrollView) findViewById(R.id.scrollView3);
        ProgressBar pb = new ProgressBar(doc_list_Activity.this);
        pb.setId(R.id.progressBar);
        ((ViewGroup) sv.getChildAt(0)).addView(pb, 0);
    }

    public View getTopCardView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlay_1);
        return linearLayout.getChildAt(0);
    }

    public View getXthCardView(int x) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlay_1);
        return linearLayout.getChildAt(x);
    }

    public int getIndexOfCard(CardView cardView) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlay_1);
        return linearLayout.indexOfChild(cardView);
    }

    public void removeProgress() {
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        ((ViewGroup) pb.getParent()).removeView(pb);
    }

    public void sendMessage(View view) {
        if (modifying()) {
            return;
        }
        setModifying();
        Intent intent = new Intent(this, DocModifyActivity.class);

        View layout = ((ViewGroup) view).getChildAt(0);
        View textV = ((ViewGroup) layout).getChildAt(0);

        TextView realTextV = (TextView) textV;
        String doc = (String) realTextV.getText();
        try {
            int id = getIndexOfCard((CardView) view);
            doc_list_Activity.setID(id);
        } catch (Exception e) {
            Log.d("e", e.toString());
        }
        intent.putExtra(EXTRA_MESSAGEDOC, doc);
        intent.putExtra("key", (String) ((List<String>) getDocAndKey().get("key")).get(doc_list_Activity.getID()));

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 1000) {
                    removeProgress();
                    YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
                    List<String> docAndKey = yunNoteApplication.getModifyResult();
                    changeXthKey(docAndKey.get(1), doc_list_Activity.getID());
                    modifyView(docAndKey.get(0), doc_list_Activity.getID());
                    blinkView(getXthCardView(doc_list_Activity.getID()));

//                    TextView textV = (TextView) findViewById(doc_list_Activity.getID());
//                    textV.setText();

                    Log.d("E", docAndKey.get(0));
                    cancelModifying();

                }
            }
        };
        YunNoteApplication yunNoteApplication = (YunNoteApplication) getApplication();
        yunNoteApplication.setHandler(handler);
        startActivity(intent);
        addProgress();
    }


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifying()) {
                    return;
                }
                addCardViewToTop("");
                addKey("", 0);
                sendMessage(getTopCardView());
            }
        });
        setAllCard();

    }
    private void initializeDocAndKey(){
        Map docAndKey = new HashMap();
        docAndKey.put("doc", new ArrayList<String>());
        docAndKey.put("key", new ArrayList<String>());
        setDocAndKey(docAndKey);
    }

//    public void onBackPressed()
//    {
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
//
//    }

    private void setAllCard() {
//        loadList();
        String response = CookieIO.getResponse();
        if(response=="0"){
            initializeDocAndKey();
            return;
        }
        List<String> docList = StringReformat.toDocList(response);
        List<String> keyList = StringReformat.toKeyList(response);
        Map docAndKey = new HashMap();
        docAndKey.put("doc", docList);
        docAndKey.put("key", keyList);
        setDocAndKey(docAndKey);
        for (int i = 0; i < docList.size(); i++) {
            addCardViewToEnd(docList.get(i));
        }
    }

    @SuppressLint("ResourceType")
    private void addCardViewToEnd(String doc) {
        LinearLayout linear = (LinearLayout) findViewById(R.id.linearlay_1);
        int index = linear.getChildCount();
        View addV = LayoutInflater.from(doc_list_Activity.this).inflate(R.layout.card_view, linear, true);
        View cardView = ((ViewGroup) addV).getChildAt(index);
        View layout = ((ViewGroup) cardView).getChildAt(0);
        TextView textView = (TextView) ((ViewGroup) layout).getChildAt(0);
        textView.setText(doc);
    }

    private void addCardViewToTop(String doc) {
        LinearLayout linear = (LinearLayout) findViewById(R.id.linearlay_1);
        View addV = LayoutInflater.from(doc_list_Activity.this).inflate(R.layout.card_view, linear, false);
        linear.addView(addV, 0);

        View layout = ((ViewGroup) addV).getChildAt(0);
        TextView textView = (TextView) ((ViewGroup) layout).getChildAt(0);
        textView.setText(doc);
    }

    private void modifyView(String doc, int index) {
        LinearLayout linear = (LinearLayout) findViewById(R.id.linearlay_1);
        View v = linear.getChildAt(index);
        View v3 = ((ViewGroup) v).getChildAt(0);
        View v2 = ((ViewGroup) v3).getChildAt(0);

        TextView x = (TextView) v2;
        x.setText(doc);
    }

    private void blinkView(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(2);
        view.startAnimation(anim);
    }

    private void loadList() {
        Handler mHandler = new Handler() {


            @Override
            public void handleMessage(Message msg) {


                if (msg.what == 1) {
                    removeProgress();
                    setDocAndKey((Map) msg.obj);
                    Map docAndKey = getDocAndKey();
                    List<String> docList = (List<String>) docAndKey.get("doc");
                    List<String> keyList = (List<String>) docAndKey.get("key");
                    for (int i = 0; i < docList.size(); i++) {
                        addCardViewToEnd(docList.get(i));
                    }

                }
            }
        };

        DownloadThread dt = new DownloadThread(mHandler);
        dt.start();
        addProgress();
    }


}
