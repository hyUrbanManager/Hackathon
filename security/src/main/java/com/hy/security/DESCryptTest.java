package com.hy.security;

import org.junit.Test;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Administrator on 2018/1/25.
 *
 * @author hy 2018/1/25
 */
public class DESCryptTest {

    public static final String key = "12345678";
    public static final String padding = "12345678";

    public static final String rawText = "hello world!";

    public static final String encryptedText = "CyqS6B+0nOHBPQsZtwf/SQ==";
    public static final String encryptedText2 = "dsoDxJZQCd4LXJWNaFyZr+0Zu8GtAXmzrSLzziLC5f0=";


    //DES,CBC,PKCS5PADDING,12345678,12345678。
    @Test
    public void encrypt() throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = factory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, desKey, new IvParameterSpec(padding.getBytes()));

        // 加密一次
//        byte[] result = cipher.doFinal(rawText.getBytes());

        // 加密三次
        byte[] last = rawText.getBytes();
        byte[] result;
        for (int i = 0; i < 3; i++) {
            last = cipher.doFinal(last);
        }
        result = last;

        System.out.println(new String(result));
        System.out.println(Formatter.toHexString(result));
        System.out.println(new String(Base64.getEncoder().encode(result)));
    }

    @Test
    public void decrypt() throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = factory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("dEs/cBc/PkCs5pAdDiNg");
        cipher.init(Cipher.DECRYPT_MODE, desKey, new IvParameterSpec(padding.getBytes()));

        // 解密一次
//        byte[] result = cipher.doFinal(Base64.getDecoder().decode(encryptedText.getBytes()));

        // 解密三次
        byte[] last = Base64.getDecoder().decode(encryptedText2.getBytes());
        byte[] result;
        for (int i = 0; i < 3; i++) {
            last = cipher.doFinal(last);
        }
        result = last;

        System.out.println(new String(result));
        System.out.println(Formatter.toHexString(result));
        System.out.println(new String(Base64.getEncoder().encode(result)));
    }

}
