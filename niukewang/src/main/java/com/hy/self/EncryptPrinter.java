package com.hy.self;

/**
 * 自己原创出题
 * 字符串加密解密。
 * i am the best coder.
 * 根据加密函数encrypt，补充完整解密函数decrypt，使得最后输出原本字符串 "i am the best coder."
 *
 * @author huangye
 */
public class EncryptPrinter {

    public static void main(String[] args) {
        String rawString = "i am the best coder.";
        String encryptString = encrypt(rawString);
        System.out.println(encryptString);
        String decryptString = decrypt(encryptString);
        System.out.println(decryptString);
    }

    private static String encrypt(String rawString) {
        StringBuilder sb = new StringBuilder();
        int start = rawString.length() / 2;
        for (int i = start; i < rawString.length(); i++) {
            char c = (char) (rawString.charAt(i) + i);
            sb.append(c);
        }
        for (int i = 0; i < start; i++) {
            char c = (char) (rawString.charAt(i) - i);
            sb.append(c);
        }
        return sb.toString();
    }

    private static String decrypt(String encryptString) {
        StringBuilder sb = new StringBuilder();
        int start = encryptString.length() / 2;
        for (int i = start; i < encryptString.length(); i++) {
            char c = (char) (encryptString.charAt(i) + i - start);
            sb.append(c);
        }
        for (int i = 0; i < start; i++) {
            char c = (char) (encryptString.charAt(i) - i - start);
            sb.append(c);
        }
        return sb.toString();
    }

}
