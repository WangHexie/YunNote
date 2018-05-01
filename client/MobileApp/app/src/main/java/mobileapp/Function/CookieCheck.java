package mobileapp.Function;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lijixuan on 2018/5/1.
 */

public class CookieCheck {

    public static String check(String cookie){
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("cookies", cookie )
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/list")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";

    }

}
