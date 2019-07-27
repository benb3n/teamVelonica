package com.example.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Random;

import static java.awt.SystemColor.text;

/**
 * Created by luqman on 27/7/2019.
 */
public class SecurityHelper {

    String key;
    Key aesKey;

    public SecurityHelper() {
        key = "Abc456jdl123lfk9";
        aesKey = new SecretKeySpec(key.getBytes(), "AES");
    }

    public byte[] encrypt(String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return encrypted;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] encrypted) {
        try {
            // decrypt the text
            Cipher hash = Cipher.getInstance("AES");
            hash.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(hash.doFinal(encrypted));
            return decrypted;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
