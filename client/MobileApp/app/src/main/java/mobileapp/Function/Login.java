package mobileapp.Function;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login {



    public static String login_check(String username,String password){

        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", username )
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://ipv4.dfen.xyz:5000/login")
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            System.out.println();
            if(!response.body().string().equals("0")){
                System.out.println(response.body().string());
                return response.body().string();
            }
            else {
                return response.body().string();
            }

        }catch (Exception e){
            e.printStackTrace();
    }

        return "0";

    }



}