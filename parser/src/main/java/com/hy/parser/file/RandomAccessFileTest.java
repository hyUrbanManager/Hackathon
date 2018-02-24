package com.hy.parser.file;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2018/2/24.
 *
 * @author hy 2018/2/24
 */

public class RandomAccessFileTest {

    @Test
    public void test1() throws IOException {
        File file = new File("F:\\AndroidStudioProject2\\Hackathon\\parser\\abc.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");

        randomAccessFile.seek(14);

        char c = (char) randomAccessFile.read();

        System.out.println(c);

        randomAccessFile.write('a');

        randomAccessFile.close();


    }

}
