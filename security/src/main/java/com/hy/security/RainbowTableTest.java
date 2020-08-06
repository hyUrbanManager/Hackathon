package com.hy.security;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;

/**
 * 生成6为数字彩虹表测试
 *
 * @author huangye
 */
public class RainbowTableTest {

    @Test
    public void generateRainbow6Num() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("md5");

        byte[] totalBytes = new byte[16 * 1024 * 1024];
        for (int i = 0; i < 1000000; i++) {
            if (i % 10000 == 0) {
                System.out.println("cal " + i);
            }
            byte[] bytes = calMD5Test(digest, intNumToString(i));
            System.arraycopy(bytes, 0, totalBytes, i * 16, bytes.length);

//            if ("04FC711301F3C784D66955D98D399AFB".equals(Formatter.toHexStringArray2(bytes).toUpperCase())) {
//                System.out.println("password: " + i);
//                return;
//            }
        }

        File file = new File("rainbowTable.dat");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(totalBytes, 0, 1000000 * 16);
    }

    private String intNumToString(int num) {
        if (num < 100000) {
            int len = String.valueOf(num).length();
            StringBuilder s = new StringBuilder(String.valueOf(num));
            for (int i = 0; i < 6 - len; i++) {
                s.insert(0, "0");
            }
            return s.toString();
        }
        return String.valueOf(num);
    }

    private byte[] calMD5Test(MessageDigest digest, String rawText) {
        return digest.digest(rawText.getBytes());
    }

    @Test
    public void readRainbowTable() throws Exception {
        String passwodEncrypt = "9c953866b6536f657c6cc8614a907cc9".toUpperCase();
//        String passwodEncrypt = "04FC711301F3C784D66955D98D399AFB";

        File file = new File("rainbowTable.dat");
        FileInputStream fis = new FileInputStream(file);

        byte[] totalByte = new byte[16 * 1024 * 1024];
        fis.read(totalByte);

        byte[] bytes = new byte[16];
        for (int i = 0; i < 1000000; i++) {
            System.arraycopy(totalByte, i * 16, bytes, 0, bytes.length);
            if (passwodEncrypt.equals(Formatter.toHexStringArray2(bytes).toUpperCase())) {
                System.out.println("password: " + i);
                return;
            }
        }
    }
}
