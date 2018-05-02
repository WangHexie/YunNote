package mobileapp.Function;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login {


    public static String login_check(String username, String password) {

        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/login")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string(); //这吊玩意只能用一次   body.string
            if (!result.equals("0")) {

                return result;
            } else {
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";

    }

    public static void main() {

    }


}