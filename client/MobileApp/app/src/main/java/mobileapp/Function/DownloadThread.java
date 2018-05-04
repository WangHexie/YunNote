package mobileapp.Function;
import 	android.os.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadThread extends Thread {

    private Handler mHandler;
    private int SUCCESS_MSG = 1;
    private int FAILURE_MSG = 0;

    public DownloadThread(Handler mHandler) {
        this.mHandler = mHandler;
    }

    /*
     * 继承重写Thread的方法
     * 实现异步请求图片
     * */
    @Override
    public void run() {
        List<String> resultDoc, resultKey;
        String jsonString = Network.getList("b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c");
        resultDoc = StringReformat.toDocList(jsonString);
        resultKey = StringReformat.toKeyList(jsonString);
        Map docAndKey = new HashMap();
        docAndKey.put("doc",resultDoc);
        docAndKey.put("key",resultKey);
        mHandler.obtainMessage(SUCCESS_MSG,docAndKey).sendToTarget();
    }
}