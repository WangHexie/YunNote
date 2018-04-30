package mobileapp.Function;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String bowlingJson(String username, String password) {
        return "{'username': '" + username + "','password' : '" + password + "'}";
    }

    public static void main(String[] args) throws IOException {
        Login example = new Login();
        String json = example.bowlingJson("admin", "admin");
        String response = example.post("http://localhost:5000/login", json);
        System.out.println(response);
    }
}