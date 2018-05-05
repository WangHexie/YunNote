package mobileapp.Function;

public class Variable {
    private static String doc;
    private static String key;

    public static String getKey() {
        return key;
    }

    public static String getDoc() {
        return doc;
    }

    public static void setDoc(String docToSet) {
        doc = docToSet;
    }

    public static void setKey(String keyToSet) {
        key = keyToSet;
    }
}
