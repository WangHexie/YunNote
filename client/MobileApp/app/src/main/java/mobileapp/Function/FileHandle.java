package mobileapp.Function;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FileHandle {
    private static String FILENAME = "docList";

    public void storeDocList(List<String> docList, Context ctx) {


        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(docList);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDocList(Context ctx) {
        List<String> docList = null;
        try {
            FileInputStream fos = ctx.openFileInput(FILENAME);
            ObjectInputStream intSt = new ObjectInputStream(fos);
            docList = (List<String>) intSt.readObject();
            return docList;
        } catch (IOException e) {
            e.printStackTrace();
            return docList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return docList;
        }

    }
}
