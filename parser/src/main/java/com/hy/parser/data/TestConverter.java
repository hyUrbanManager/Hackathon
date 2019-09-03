package com.hy.parser.data;

import java.util.Arrays;

public class TestConverter {

    public static void main(String[] args) {

        Converter converter = new Converter();

        byte[] bytes = new byte[12];
        bytes[0] = 0x12;
        bytes[1] = 0x34;
        bytes[2] = 0x56;
        bytes[3] = 0x78;

        int num = converter.byte2Int(bytes, 0);

        System.out.println("num: " + num + ", bytes: " + Arrays.toString(bytes));

        int num2 = 3000;
        converter.int2Byte(bytes, 4, num2);
        num2 = converter.byte2Int(bytes, 4);
        System.out.println("num2: " + num2 + ", bytes: " + Arrays.toString(bytes));

    }

}
