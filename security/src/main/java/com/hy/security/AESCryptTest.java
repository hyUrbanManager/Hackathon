package com.hy.security;

import org.junit.Test;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 *
 * @author huangye
 */
public class AESCryptTest {

    // 固定的key
    public static final String aesKey = "53ff840581a65a994a304d0fa7307dfd";

    public static final String rawText = "hello world!";
    public static final String encryptedText = "KHiWFwn48W4Wc0/K0LTMNg==";

    @Test
    public void encrypt() throws Exception {
        // 生成一个key密钥
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey geneKey = generator.generateKey();
        System.out.println("gen key: " + Formatter.toHexString(geneKey.getEncoded()));

        SecretKeySpec key = new SecretKeySpec(Formatter.hexStringToBytes(aesKey), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] result = cipher.doFinal(rawText.getBytes());

        System.out.println(new String(result));
        System.out.println(Formatter.toHexString(result));
        System.out.println(new String(Base64.getEncoder().encode(result)));
    }

    @Test
    public void decrypt() throws Exception {
        SecretKeySpec key = new SecretKeySpec(Formatter.hexStringToBytes(aesKey), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] result = cipher.doFinal(Base64.getDecoder().decode(encryptedText.getBytes()));

        System.out.println(new String(result));
        System.out.println(Formatter.toHexString(result));
        System.out.println(new String(Base64.getEncoder().encode(result)));
    }

}
