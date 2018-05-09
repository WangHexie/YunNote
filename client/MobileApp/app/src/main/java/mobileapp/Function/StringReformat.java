package mobileapp.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringReformat {

    private static List<String> listFormat(String keyWord,String jsonString){
        List<String> docList = new ArrayList();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = (JSONObject) obj;
        JSONArray array = (JSONArray) obj2.get(keyWord);
        for (int i = 0; i < array.size(); i++) {
            docList.add((String) array.get(i));
        }
        return docList;
    }

    private static List<Long> listFormatInLong(String keyWord,String jsonString){
        List<Long> docList = new ArrayList();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = (JSONObject) obj;
        JSONArray array = (JSONArray) obj2.get(keyWord);
        for (int i = 0; i < array.size(); i++) {
            docList.add((Long) array.get(i));
        }
        return docList;
    }

    public static List<String> toDocList(String jsonString) {
        return listFormat("list",jsonString);
    }

    public static String toDoc(String jsonString) {
        return listFormat("doc",jsonString).get(0);
    }


    public static List<Long> toTimeList(String jsonString) {
        return listFormatInLong("time",jsonString);
    }

    public static List<String> toKeyList(String jsonString) {
        return listFormat("key",jsonString);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String y = sc.nextLine();
        Network.print(StringReformat.toTimeList(y));
        //{"list": ["879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "big"]}
    }
}

