package com.hy.androidlib.media.proxy;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 音乐文件的自定义文件头。
 *
 * @author hy 2018/2/8
 */
public class MusicHead {

    // 文件长度。
    public long contentLength;

    // ETag。
    public String ETag;

    /**
     * 从输出流读出自身数据。
     *
     * @param is
     * @return
     */
    public static MusicHead readFromInputStream(InputStream is) {
        MusicHead musicHead = new MusicHead();
        byte[] bytes = new byte[4];
        try {
            int len = is.read(bytes);
            if (len != 4) {
                Log.e(MediaPlayerProxy.TAG + " MusicHead", "read bytes len is not 4");
                musicHead.contentLength = Long.MAX_VALUE;
            } else {
                musicHead.contentLength = bytesToLong(bytes);
            }

            StringBuilder sb = new StringBuilder();
            char c = (char) is.read();
            if (c == '\"') {
                sb.append('\"');
                while ((c = (char) is.read()) != '\"') {
                    sb.append(c);
                }
                sb.append('\"');
                musicHead.ETag = sb.toString();
            } else {
                Log.e(MediaPlayerProxy.TAG + " MusicHead", "ETag is not start with \"");
                musicHead.ETag = "\"1\"";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(MediaPlayerProxy.TAG + " MusicHead", "readFromInputStream len is " + musicHead.contentLength);
        return musicHead;
    }

    /**
     * 把自身数据写入流。
     *
     * @param os
     */
    public void writeToOutputStream(OutputStream os) {
        int headLen = getHeadLen();
        byte[] bytes = longToBytes(contentLength);
        try {
            os.write(bytes);
            os.write(ETag.getBytes());
            Log.i(MediaPlayerProxy.TAG + " MusicHead", "writeToOutputStream len is " + headLen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取音乐头的长度。
     *
     * @return
     */
    public int getHeadLen() {
        if (ETag == null) {
            throw new RuntimeException("ETag is null");
        }
        return 4 + ETag.getBytes().length;
    }

    private static long bytesToLong(byte[] bytes) {
        if (bytes.length != 4) {
            throw new RuntimeException("bytes length is not 4.");
        }
        int shiftNum = 0;
        long result = 0;
        for (int i = bytes.length - 1; i >= 0; i--) {
            result += (0xff & bytes[i]) << shiftNum;
            shiftNum += 8;
        }
        return result;
    }

    private static byte[] longToBytes(long l) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (l >> 24 & 0xff);
        bytes[1] = (byte) (l >> 16 & 0xff);
        bytes[2] = (byte) (l >> 8 & 0xff);
        bytes[3] = (byte) (l & 0xff);
        return bytes;
    }

}
