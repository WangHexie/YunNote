package com.app.mobile.mobileapp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash{
    public static String hash(String password) {
        String hashResult = password;
        for(int i=0;i<800;i++){
            hashResult = singleHash(hashResult+"hashSalt");
        }
        return hashResult;
    }

    private static String singleHash(String word){
        String strResult = null;
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(word.getBytes( "UTF-8" ));
            byte byteBuffer[] = messageDigest.digest();
            StringBuffer strHexString = new StringBuffer();
            for (int i = 0; i < byteBuffer.length; i++)
            {
                String hex = Integer.toHexString(0xff & byteBuffer[i]);
                if (hex.length() == 1)
                {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            strResult = strHexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return strResult;
    }

    public static void main(String [] args) {
        System.out.println(hash("ppp"));
    }
}