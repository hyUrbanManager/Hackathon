package com.compress;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 生成被压缩文件。
 *
 * @author hy 2017/11/18
 */
public class GenerateCompressData {

    static final char[] letters = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', ' ',
    };

    public static void generateText(String path, int len) {
        File file = new File(path);
        Random random = new Random();

        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int r = 0;
            for (int i = 0; i < len / 4; i++) {
                int index = random.nextInt(letters.length);
                os.write(letters[index]);
                os.write(letters[index]);
                os.write(letters[index]);
                os.write(letters[index]);
                r++;
                if (r >= 20) {
                    os.write('\n');
                    r = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
    }

    public static void generateText2(String path, int len) {
        File file = new File(path);
        Random random = new Random();
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int r = 0;
            int index = random.nextInt(letters.length);
            for (int i = 0; i < len; i++) {
                os.write(letters[index]);
                r++;
                if (r >= 80) {
                    os.write('\n');
                    r = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
    }

    static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
