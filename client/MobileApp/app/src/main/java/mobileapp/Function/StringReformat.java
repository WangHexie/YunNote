package mobileapp.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringReformat {
    public static List<String> toDocList(String jsonString) {
        List<String> docList = new ArrayList();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = (JSONObject) obj;
        JSONArray array = (JSONArray) obj2.get("list");
        for (int i = 0; i < array.size(); i++) {
            docList.add((String) array.get(i));
        }
        return docList;
    }

    public static List<String> toKeyList(String jsonString) {
        List<String> docList = new ArrayList();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = (JSONObject) obj;
        JSONArray array = (JSONArray) obj2.get("key");
        for (int i = 0; i < array.size(); i++) {
            docList.add((String) array.get(i));
        }
        return docList;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String y = sc.nextLine();
        Network.print(StringReformat.toDocList(y));
        //{"list": ["879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "879546", "big"]}
    }
}

