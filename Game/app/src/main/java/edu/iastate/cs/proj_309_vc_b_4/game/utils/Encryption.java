package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * offers a method to encrypt a string using MD5
 * Created by johan on 02.10.2017.
 */

public class Encryption {

    private Encryption(){};


    /**
     * Will encrypt the string into MD5-hash. no salting is done.
     * @param s string to be encrypted
     * @return the encrypted string.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String hash(String s){
        MessageDigest messageDigest = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());

            byte[] bytes = digest.digest();
            //convert to hexadecicmal
            String hashed = toHex(bytes);
            return hashed;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Converts an array of bytes into a readable string
     * @param bytes
     * @return the string in hex.
     */
    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            sb.append(Integer.toString((b & 0xff)+0x100,16).substring(1));
        }
        return sb.toString();
    }
}
