package com.hy.androidlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;


import com.hy.androidlib.Logcat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件操作工具类
 *
 * @author zqjasonZhong
 *         date : 2017/9/19
 */
public class FileUtil {

    private static String TAG = "FileUtil";

    public final static int FILE_TYPE_UNKNOWN = 0;
    public final static int FILE_TYPE_PIC = 1;
    public final static int FILE_TYPE_VIDEO = 2;

    /**
     * 检查文件是否存在。
     *
     * @param filePath 文件路径
     */
    public static boolean checkFileExist(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            return file.exists();
        }
        return false;
    }

    /**
     * 判断文件类型。
     *
     * @param filename 文件名
     */
    public static int judgeFileType(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            if ((filename.endsWith(".png") || filename.endsWith(".PNG")
                    || filename.endsWith(".JPEG") || filename.endsWith(".jpeg")
                    || filename.endsWith(".jpg") || filename.endsWith(".JPG"))) {
                return FILE_TYPE_PIC;
            } else if ((filename.endsWith(".mov") || filename.endsWith(".MOV")
                    || filename.endsWith(".mp4") || filename.endsWith(".MP4")
                    || filename.endsWith(".avi") || filename.endsWith(".AVI"))) {
                return FILE_TYPE_VIDEO;

            }
        }
        return FILE_TYPE_UNKNOWN;
    }

    /**
     * 删除文件或文件夹。
     *
     * @param file 文件或文件夹
     */
    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            if (file.delete()) {
                Logcat.i(TAG, "delete file success!");
            }
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                if (file.delete()) {
                    Logcat.i(TAG, "delete empty file success!");
                }
                return;
            }
            for (File childFile : childFiles) {
                deleteFile(childFile);
            }
            if (file.delete()) {
                Logcat.i(TAG, "delete empty file success!");
            }
        }
    }

    /**
     * 把Bitmap保存为文件。
     *
     * @param bitmap     Bitmap数据
     * @param outputPath 输出路径
     * @param quality    压缩比例(0-100)
     */
    public static boolean bitmapToFile(Bitmap bitmap, String outputPath, int quality) {
        FileOutputStream outStream = null;
        boolean result = false;
        if (bitmap != null && !TextUtils.isEmpty(outputPath)) {
            try {
                outStream = new FileOutputStream(outputPath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outStream != null) {
                    try {
                        outStream.flush();
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 数据保存成文件。
     *
     * @param data       数据
     * @param outputPath 输出文件路径
     * @return 是否成功
     */
    public static boolean bytesToFile(byte[] data, String outputPath) {
        if (data != null && !TextUtils.isEmpty(outputPath)) {
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(outputPath);
                outputStream.write(data);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }


    /**
     * 拼接目录路径
     *
     * @param rootName     根路径名称
     * @param oneDirName   一级目录名称
     * @param twoDirName   二级目录名称
     * @param threeDirName 三级目录名称
     * @return 拼合的路径
     */
    public static String splicingFilePath(String rootName, String oneDirName, String twoDirName, String threeDirName) {
        File file;
        String path = Environment.getExternalStorageDirectory().getPath();
        if (!TextUtils.isEmpty(rootName)) {
            if (rootName.contains(File.separator)) {
                String[] dirNames = rootName.split(File.separator);
                for (String name : dirNames) {
                    if (!TextUtils.isEmpty(name)) {
                        path += File.separator + name;
                        file = new File(path);
                        if (!file.exists()) {
                            if (file.mkdir()) {
                                Logcat.w(TAG, "create root dir success! path : " + path);
                            }
                        }
                    }
                }
            } else {
                path += File.separator + rootName;
                file = new File(path);
                if (!file.exists()) {
                    if (file.mkdir()) {
                        Logcat.w(TAG, "create root dir success! path : " + path);
                    }
                }
            }
            if (TextUtils.isEmpty(oneDirName)) {
                return path;
            }
            path = path + File.separator + oneDirName;
            file = new File(path);
            if (!file.exists()) {
                if (file.mkdir()) {
                    Logcat.w(TAG, "create one dir success!");
                }
            }
            if (TextUtils.isEmpty(twoDirName)) {
                return path;
            }
            path = path + File.separator + twoDirName;
            file = new File(path);
            if (!file.exists()) {
                if (file.mkdir()) {
                    Logcat.w(TAG, "create two dir success!");
                }
            }
            if (TextUtils.isEmpty(threeDirName)) {
                return path;
            }
            path = path + File.separator + threeDirName;
            file = new File(path);
            if (!file.exists()) {
                if (file.mkdir()) {
                    Logcat.w(TAG, "create three sub dir success!");
                }
            }
        }
        return path;
    }


    /**
     * 拷贝assets文件中的文件到SD卡
     * @param context
     * @param sourceName
     * @param destPath
     */
    public static void copyFromAssetsToSdcard(Context context, boolean isCover, String sourceName, String destPath) {
        if (!TextUtils.isEmpty(sourceName) && !TextUtils.isEmpty(destPath)) {
            File file = new File(destPath);
            if (isCover || (!isCover && !file.exists())) {
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    is = context.getResources().getAssets().open(sourceName);
                    fos = new FileOutputStream(destPath);
                    byte[] buffer = new byte[1024];
                    int size;
                    while ((size = is.read(buffer, 0, 1024)) >= 0) {
                        fos.write(buffer, 0, size);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 将文件转为byte[]
     * @param filePath 文件路径
     */
    public static byte[] getBytes(String filePath){
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        byte[] dataBuf = null;
        File file = new File(filePath);
        ByteArrayOutputStream out = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
//            Debug.w(TAG, "file len : " + in.available());
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {

                out.write(b, 0, b.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dataBuf = out.toByteArray();
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataBuf;
    }
}
