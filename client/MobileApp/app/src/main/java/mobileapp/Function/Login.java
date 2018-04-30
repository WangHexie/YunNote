package mobileapp.Function;





import org.json.JSONObject;
import org.json.JSONStringer;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Lijixuan on 2018/4/30.
 */

public class Login {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String username;
    private String password;
    private static String url = "http://localhost:5000/login?username=admin&password=admin";

    public static boolean loginpost(String username,String password) throws Exception{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.code());
        return true;

    }


}
