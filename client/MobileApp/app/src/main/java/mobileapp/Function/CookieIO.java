package mobileapp.Function;



public class CookieIO {
    private static String cookie;
    private static String response;


    public static void setCookie(String c){
        cookie = c;
    }

    public static String getCookie(){
        return cookie;
    }

    public static String getResponse(){return response;}
    public static void setResponse(String a){response = a;}


}
