package com.app.mobile.mobileapp;

import android.app.Application;
import android.os.Handler;

public class YunNoteApplication extends Application {

    private Handler handler = null;

    // set方法
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    // get方法
    public Handler getHandler() {
        return handler;
    }
}
