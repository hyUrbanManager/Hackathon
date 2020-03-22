package com.hy.security;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2018/1/25.
 *
 * @author hy 2018/1/25
 */
public class MD5CryptTest {

    public static final String rawText = "hello, world!";

    public static final String filePath = "E:\\安装包\\pycharm-community-2017.2.4.exe";

    @Test
    public void calMD5Test() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] result = digest.digest(rawText.getBytes());
        System.out.println(result.length);

        System.out.println(Formatter.toHexStringArray2(result));
    }

    @Test
    public void calMD5Test2() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("md5");
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);

        long start = System.currentTimeMillis();

        byte[] buffers = new byte[1024 * 500];
        int len;
        int readTimes = 1;
        while ((len = dis.read(buffers)) != -1) {
            digest.update(buffers, 0, len);
        }

        byte[] result = digest.digest();

        System.out.println(Formatter.toHexStringArray2(result));

        dis.close();
        fis.close();

        System.out.println();
        System.out.println("duration: " + (System.currentTimeMillis() - start));
    }
}
