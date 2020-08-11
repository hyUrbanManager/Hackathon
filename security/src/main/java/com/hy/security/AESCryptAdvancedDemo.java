package com.hy.security;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密高级超长加密
 *
 * @author huangye
 */
public class AESCryptAdvancedDemo {

    // 固定的key
    public static final String aesKey = "6ed59130d1a3077e5ca3de7e141487d1e2ca65a267a49e91ec3d8236c58440cf";

    @Test
    public void genKey() throws Exception {
        // 生成一个key密钥
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        SecretKey geneKey = generator.generateKey();
        System.out.println("gen key: " + Formatter.toHexString(geneKey.getEncoded()));
    }

    @Test
    public void encrypt() throws Exception {
        SecretKeySpec key = new SecretKeySpec(Formatter.hexStringToBytes(aesKey), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream fis = new FileInputStream(new File("article.txt"));
        byte[] bytes = new byte[100 * 1024];
        int len = fis.read(bytes);
        fis.close();

        byte[] result = cipher.doFinal(bytes, 0, len);

        System.out.println("encrypt: " + result.length);

        byte[] encryptContent = Base64.getEncoder().encode(result);

        FileOutputStream fos = new FileOutputStream(new File("encrypt_article.txt"));
        fos.write(encryptContent);
        fos.close();
    }

    @Test
    public void decrypt() throws Exception {
        SecretKeySpec key = new SecretKeySpec(Formatter.hexStringToBytes(aesKey), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream fis = new FileInputStream(new File("encrypt_article.txt"));
        byte[] bytes = new byte[100 * 1024];
        int len = fis.read(bytes);
        fis.close();

        byte[] realBytes = new byte[len];
        System.arraycopy(bytes, 0, realBytes, 0, len);
        byte[] encryptContent = Base64.getDecoder().decode(realBytes);

        byte[] result = cipher.doFinal(encryptContent);

        System.out.println("decrypt: " + result.length);

        FileOutputStream fos = new FileOutputStream(new File("decrypt_article.txt"));
        fos.write(result);
        fos.close();
    }

}
