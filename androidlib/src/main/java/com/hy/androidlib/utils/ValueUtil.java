package com.hy.androidlib.utils;

import android.content.Context;
import android.util.TypedValue;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 转换者。包括以下转换工具：
 * <table border>
 * <caption>Android、手机</caption>
 * <tr>
 * <th align=left>函数
 * <th align=left>描述
 * <th align=left>例子
 * <tr>
 * <td>{@link #dp2px(Context, int)}
 * <td>将dp转换成为px单位。
 * <td>100dp-&gt;200px
 * <tr>
 * <td>{@link #formatTime(int)}
 * <td>把数字音乐时长毫秒数转换成为几分几秒的格式。
 * <td>210000-&gt;03:30
 * <tr>
 * <td>{@link #isMobileNum(String)}
 * <td>检测字符串是否是手机号码。
 * <td>13610987553-&gt;true
 * </table>
 * <p>
 * <table border>
 * <caption>字符、字符串转换</caption>
 * <tr>
 * <th align=left>函数
 * <th align=left>描述
 * <th align=left>例子
 * <tr>
 * <td>{@link #checkHexStr(String)}
 * <td>检查16进制字符串是否有效
 * <td>0F018A7B5c-&gt;true
 * <tr>
 * <td>{@link #str2HexStr(String)}
 * <td>字符串转换成十六进制字符串
 * <td>*~XXXX$#*-&gt;61 62 7c 90
 * <tr>
 * <td>{@link #hexStr2Str(String)}
 * <td>十六进制字符串转换成 ASCII字符串
 * <td>61 62 63-&gt;abc
 * <tr>
 * <td>{@link #byte2HexStr(byte[], int)}
 * <td>bytes转换成十六进制字符串
 * <td>[0x61,0x62,0x63]-&gt;61 62 63
 * <tr>
 * <td>{@link #int2HexStr(int[], int)}
 * <td>ints转换成十六进制字符串
 * <td>[61,62,63]-&gt;61 62 63
 * <tr>
 * <td>{@link #bytes2String(byte[], int)}
 * <td>数组转成数字输出
 * <td>[0x61,0x62,0x63]-&gt;97 98 99
 * <tr>
 * <td>{@link #hexStr2Bytes(String)}
 * <td>bytes字符串转换为Byte值
 * <td>61 62 63-&gt;[0x61,0x62,0x63]
 * <tr>
 * <td>{@link #strToUnicode(String)}
 * <td>String的字符串转换成unicode的String
 * <td>你好-&gt;\u4f60\u597d
 * <tr>
 * <td>{@link #unicodeToString(String)}
 * <td>unicode的String转换成String的字符串
 * <td>\u4f60\u597d-&gt;你好
 * <tr>
 * <td>{@link #intToHexString(int)}
 * <td>Int转16进制字符串
 * <td>16-&gt;10
 * <tr>
 * <td>{@link #intToByte(int)}
 * <td>Int转Byte
 * <td>16-&gt;0x10
 * <tr>
 * <td>{@link #byteToInt(byte)}
 * <td>Byte 转 整型
 * <td>0x21-&gt;33
 * <tr>
 * <td>{@link #byteToHexString(byte)}
 * <td>Byte转Hex
 * <td>0x10-&gt;10
 * <tr>
 * <td>{@link #intToBytes(int)}
 * <td>将int类型的数据转换为byte数组 原理：将int数据中的四个byte取出，分别存储
 * <td>305419896-&gt;[0x78,0x56,0x34,0x12]
 * <tr>
 * <td>{@link #bytesToInt(byte, byte)}
 * <td>将byte类型的数据转换为int数组
 * <td>0x12,0x34-&gt;4660
 * <tr>
 * <td>{@link #shortToBytes(short)}
 * <td>short原理：将short数据中的两个byte取出，分别存储
 * <td>4660-&gt;0x12,0x34
 * <tr>
 * <td>{@link #cnStrToNumber(String)}
 * <td>中文数字字符转阿拉伯数字。支持万以下。
 * <td>二十三-&gt;23
 * </table>
 *
 * @author hy 2018/2/2
 */
public class ValueUtil {

    /**
     * dp covert to px
     *
     * @param context 上下文
     * @param dp      dp
     */
    public static int dp2px(Context context, int dp) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }


    /**
     * 格式化时间。
     *
     * @param time 时间
     */
    public static String formatTime(int time) {
        if (time <= 0) {
            return "00:00";
        }
        StringBuilder sb = new StringBuilder();
        time /= 1000;
        //小时
        if (time / 3600 > 0) {
            sb.append(String.format(Locale.CHINA, "%02d", time / 3600));
            sb.append(":");
        }
        //分钟
        if (time / 60 > 0) {
            sb.append(String.format(Locale.CHINA, "%02d", time % 3600 / 60));
            sb.append(":");
        } else {
            sb.append("00:");
        }
        //秒
        sb.append(String.format(Locale.CHINA, "%02d", time % 60));
        return sb.toString();
    }

    /**
     * 验证手机号码。
     *
     * @param phoneNum 手机文本。
     */
    public static boolean isMobileNum(String phoneNum) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(14[0-9]))\\d{8}$");
        return p.matcher(phoneNum).matches();
    }


    private final static char[] mChars = "0123456789ABCDEF".toCharArray();
    private final static String mHexStr = "0123456789ABCDEF";

    /**
     * 检查16进制字符串是否有效
     *
     * @param sHex String 16进制字符串
     * @return boolean
     */
    public static boolean checkHexStr(String sHex) {
        String sTmp = sHex.trim().replace(" ", "").toUpperCase(Locale.US);
        int iLen = sTmp.length();

        if (iLen > 1 && iLen % 2 == 0) {
            for (int i = 0; i < iLen; i++)
                if (!mHexStr.contains(sTmp.substring(i, i + 1)))
                    return false;
            return true;
        } else
            return false;
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str String 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {
        StringBuilder sb = new StringBuilder();
        byte[] bs = null;
        try {
            bs = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (bs == null) {
            return "";
        }

        for (byte b : bs) {
            sb.append(mChars[(b & 0xFF) >> 4]);
            sb.append(mChars[b & 0x0F]);
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制字符串转换成 ASCII字符串
     *
     * @param hexStr String Byte字符串
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {
        hexStr = hexStr.trim().replace(" ", "").toUpperCase(Locale.US);
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int iTmp = 0x00;

        for (int i = 0; i < bytes.length; i++) {
            iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;
            iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (iTmp & 0xFF);
        }
        String result = "";
        try {
            result = new String(bytes, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b    byte[] byte数组
     * @param iLen int 取前N位处理 N=iLen
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b, int iLen) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(mChars[(b[n] & 0xFF) >> 4]);
            sb.append(mChars[b[n] & 0x0F]);
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    /**
     * int
     *
     * @param b    byte[] byte数组
     * @param iLen int 取前N位处理 N=iLen
     * @return String int
     */
    public static String int2HexStr(int[] b, int iLen) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(mChars[(((byte) b[n]) & 0xFF) >> 4]);
            sb.append(mChars[((byte) b[n]) & 0x0F]);
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    public static String bytes2String(byte[] b, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(String.valueOf(b[i]));
        }
        return sb.toString();
    }

    /**
     * bytes字符串转换为Byte值
     *
     * @param src String Byte字符串，每个Byte之间没有分隔符(字符范围:0-9 A-F)
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        /*对输入值进行规范化整理*/
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);
        //处理值初始化
        int m = 0, n = 0;
        int iLen = src.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间

        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return ret;
    }

    /**
     * String的字符串转换成unicode的String
     *
     * @param strText String 全角字符串
     * @return String 每个unicode之间无分隔符
     * @throws Exception
     */
    public static String strToUnicode(String strText)
            throws Exception {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u");
            else // 低位在前面补00
                str.append("\\u00");
            str.append(strHex);
        }
        return str.toString();
    }

    /**
     * unicode的String转换成String的字符串
     *
     * @param hex String 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串
     */
    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        int iTmp = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 将16进制的string转为int
            iTmp = (Integer.valueOf(s.substring(2, 4), 16) << 8) | Integer.valueOf(s.substring(4), 16);
            // 将int转换为字符
            str.append(new String(Character.toChars(iTmp)));
        }
        return str.toString();
    }

    /**
     * Int 转 16进制字符串
     *
     * @param num 整型数字
     * @return 16进制字符串
     */
    public static String intToHexString(int num) {
        return String.format("%02x", num);
    }

    /**
     * Int 转 Byte
     *
     * @param num 整型
     * @return Byte
     */
    public static byte intToByte(int num) {
        return (byte) num;
    }

    /**
     * Byte 转 整型
     *
     * @param b 字节
     * @return 整型
     */
    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    /**
     * Byte 转 Hex
     */
    public static String byteToHexString(byte b) {
        return intToHexString(b & 0xFF);
    }

    /**
     * 将int类型的数据转换为byte数组
     * 原理：将int数据中的四个byte取出，分别存储
     *
     * @param n int数据
     * @return 生成的byte数组
     */
    public static byte[] intToBytes(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }


    /**
     * 将byte类型的数据转换为int数组
     *
     * @param h 高位
     * @return l 低位
     */
    public static int bytesToInt(byte h, byte l) {
        int result = (0xff & h) << 8;
        result = result + (0xff & l);
        return result;
    }

    /**
     * short
     * 原理：将short数据中的两个byte取出，分别存储
     *
     * @param n short数据
     * @return 生成的byte数组
     */
    public static byte[] shortToBytes(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }

    //数字位
    private static char[] chnNum = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
    private static HashMap<Character, Integer> table = new HashMap<>();

    static {
        for (int i = 0; i < chnNum.length; i++) {
            table.put(chnNum[i], i);
        }
        table.put('十', 10);
        table.put('百', 100);
        table.put('千', 1000);
    }

    /**
     * 中文数字字符转阿拉伯数字。支持万以下。
     *
     * @param str
     * @return
     */
    public static int cnStrToNumber(String str) {
        // 去除'零'。
        str = str.replace("零", "");
        if (table.get(str.charAt(0)) >= 10) {
            str = "一" + str;
        }

        int value = 0;
        int sectionNum = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int v = table.get(c);
            if (v == 10 || v == 100 || v == 1000) {
                value += sectionNum * v;
            } else if (i == chars.length - 1) {
                value += v;
            } else {
                sectionNum = v;
            }
        }
        return value;
    }

}
