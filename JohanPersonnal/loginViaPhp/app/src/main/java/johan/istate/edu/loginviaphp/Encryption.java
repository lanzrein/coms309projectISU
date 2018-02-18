package johan.istate.edu.loginviaphp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.style.AlignmentSpan;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by johan on 02.10.2017.
 */

public class Encryption {


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
     * @return
     */
    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            sb.append(Integer.toString((b & 0xff)+0x100,16).substring(1));
        }
        return sb.toString();
    }
}
