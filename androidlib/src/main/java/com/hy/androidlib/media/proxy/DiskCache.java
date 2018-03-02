package com.hy.androidlib.media.proxy;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 磁盘缓存管理器，管理缓存的音乐文件。
 *
 * @author hy 2018/2/6
 */
public class DiskCache {

    private File cacheDirectory;

    private MessageDigest md5Digest;

    /**
     * 构造器，传入缓存的目录。
     *
     * @param cacheDirectory
     */
    public DiskCache(File cacheDirectory) {
        if (cacheDirectory == null) {
            throw new RuntimeException("param cacheDirectory can not be null.");
        }
        this.cacheDirectory = cacheDirectory;

        // 递归创建文件夹。
        if (!cacheDirectory.exists()) {
            List<File> list = new ArrayList<>();
            File file = cacheDirectory.getParentFile();
            while (!file.exists()) {
                list.add(file);
                file = file.getParentFile();
            }
            Collections.reverse(list);
            for (File f : list) {
                f.mkdir();
            }
            cacheDirectory.mkdir();
        }
        try {
            md5Digest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            Log.i(MediaPlayerProxy.TAG + " diskCache", "", e);
        }
    }

    public static final int NO_CACHE = 0;
    public static final int CACHING = 1;
    public static final int FULL_CACHE = 2;

    /**
     * 该Url的歌曲在磁盘的缓存状态。
     *
     * @param url
     * @return {@link #NO_CACHE} {@link #CACHING} {@link #FULL_CACHE}
     */
    public int getCacheState(String url) {
        File[] files = cacheDirectory.listFiles();
        if (files == null) {
            return NO_CACHE;
        }
        Set<String> set = new HashSet<>();
        for (File f : files) {
            set.add(f.getName());
        }
        String md5FileName = md5(url);
        if (md5FileName.equals("tmp")) {
            return NO_CACHE;
        }
        if (!set.contains(md5FileName)) {
            return NO_CACHE;
        }

        // 打开文件验证头部。
        File file = new File(cacheDirectory, md5FileName);
        long realFileLen = file.length();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            MusicHead musicHead = MusicHead.readFromInputStream(fis);
            Log.i(MediaPlayerProxy.TAG + " diskCache", "head save len: " + musicHead.contentLength +
                    ", real file len: " + realFileLen);

            // 查看存储长度是否足够。
            if (musicHead.contentLength - (realFileLen - musicHead.getHeadLen()) > 0) {
                return CACHING;
            } else {
                return FULL_CACHE;
            }
        } catch (FileNotFoundException e) {
            return NO_CACHE;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * url生成文件。
     *
     * @param url
     * @return
     */
    public File generateCacheFileFromUrl(String url) {
        File file = new File(cacheDirectory, md5(url));
        Log.i(MediaPlayerProxy.TAG + " diskCache", "save file path: " + file.getAbsolutePath());
        return file;
    }


    /**
     * 对url作md5信息摘要，使用Base64显示。例如：4HavZDkvcUohLDHXvbowOw==
     * 当里面字符含有文件分隔符时，替换成*字符。
     *
     * @param url 歌曲Url
     * @return
     */
    private String md5(String url) {
        if (md5Digest != null) {
            byte[] result = md5Digest.digest(url.getBytes());
            String name = Base64.base64Encode(result);
            name = name.replace("/", "*");
            return name;
        } else {
            return "tmp";
        }
    }

    private static class Base64 {

        private static final char LAST2BYTE = (char) Integer.parseInt("00000011", 2);
        private static final char LAST4BYTE = (char) Integer.parseInt("00001111", 2);
        private static final char LAST6BYTE = (char) Integer.parseInt("00111111", 2);
        private static final char LEAD6BYTE = (char) Integer.parseInt("11111100", 2);
        private static final char LEAD4BYTE = (char) Integer.parseInt("11110000", 2);
        private static final char LEAD2BYTE = (char) Integer.parseInt("11000000", 2);
        private static final char[] ENCODE_TABLE = new char[]{
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

        private static String base64Encode(byte[] from) {
            StringBuilder to = new StringBuilder((int) (from.length * 1.34) + 3);
            int num = 0;
            char currentByte = 0;
            for (int i = 0; i < from.length; i++) {
                num = num % 8;
                while (num < 8) {
                    switch (num) {
                        case 0:
                            currentByte = (char) (from[i] & LEAD6BYTE);
                            currentByte = (char) (currentByte >>> 2);
                            break;
                        case 2:
                            currentByte = (char) (from[i] & LAST6BYTE);
                            break;
                        case 4:
                            currentByte = (char) (from[i] & LAST4BYTE);
                            currentByte = (char) (currentByte << 2);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & LEAD2BYTE) >>> 6;
                            }
                            break;
                        case 6:
                            currentByte = (char) (from[i] & LAST2BYTE);
                            currentByte = (char) (currentByte << 4);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & LEAD4BYTE) >>> 4;
                            }
                            break;
                    }
                    to.append(ENCODE_TABLE[currentByte]);
                    num += 6;
                }
            }
            if (to.length() % 4 != 0) {
                for (int i = 4 - to.length() % 4; i > 0; i--) {
                    to.append("=");
                }
            }
            return to.toString();
        }
    }
}
