package com.perfect.tabkiosk.Helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Common {

    public static String BASE_URL = "https://202.21.32.35:14001/cusFeedback/api/";
    public static String CERT_NAME = "client-demo.pem";
    private static final String ASCII                                   = "ASCII";



    public static String encryptStart(String encypt) throws Exception {
        String te = encypt;

        String encrypted = encrypt(te);
        return encrypted;
    }
    private static String encrypt(String inputText) throws Exception {
        String s = "Agentscr";
        byte[] keyValue = s.getBytes("US-ASCII");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            KeySpec keySpec = new DESKeySpec(keyValue);
            SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
            IvParameterSpec iv = new IvParameterSpec(keyValue);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            bout.write(cipher.doFinal(inputText.getBytes("ASCII")));
        } catch (Exception e) {
            System.out.println("Exception .. " + e.getMessage());
        }

        return new String(Base64.encode(bout.toByteArray(), Base64.DEFAULT), "ASCII");

    }



}
