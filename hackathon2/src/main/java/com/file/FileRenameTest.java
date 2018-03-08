package com.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2018/3/7.
 *
 * @author hy 2018/3/7
 */
public class FileRenameTest {

    @Test
    public void test() throws Exception {
        File f1 = new File("G:\\jvm\\a.txt");
        File f2 = new File("G:\\jvm\\b.txt");

        if (f1.exists()) {
            System.out.println(f1.getPath() + " -> " + f2.getPath());
            FileInputStream fis = new FileInputStream(f1);
            byte[] bytes = new byte[1024];
            int len = fis.read(bytes);
            System.out.println(new String(bytes, 0, len));
            boolean isSuccess = f1.renameTo(f2);
            System.out.println("result: " + isSuccess);
        } else if (f2.exists()) {
            System.out.println(f2.getPath() + " -> " + f1.getPath());
            FileInputStream fis = new FileInputStream(f2);
            byte[] bytes = new byte[1024];
            int len = fis.read(bytes);
            System.out.println(new String(bytes, 0, len));
            boolean isSuccess = f2.renameTo(f1);
            System.out.println("result: " + isSuccess);
        } else {
            System.out.println("no file");
        }

    }

    @Test
    public void test2() {
        String s = "zxcvbnm";
        String a = "vbn";
        if (s.contains(a)) {

        }


    }

}
