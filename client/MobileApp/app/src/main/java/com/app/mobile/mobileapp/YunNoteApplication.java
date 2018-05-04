package com.app.mobile.mobileapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class YunNoteApplication extends Application {

    private Handler handler = null;
    private List<String> list = new ArrayList<>();



    // set方法
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    // get方法
    public Handler getHandler() {
        return handler;
    }


    public List<String> getModifyResult(){   //得到编辑后的 list 存储doc key
        return list;
    }

    public void ListAdd(String add){ //先加 doc 后key
        list.add(add);
    }

    public  void cleanList(){
        list = new ArrayList<>();
    }





}
