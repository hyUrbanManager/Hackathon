package com.hy.parser.data;

/**
 * 基本数据类型转换。
 *
 * @author huangye
 */
public class Converter {

    public int byte2Int(byte[] content, int index) {
        return (content[index] & 0xFF) << 24
                | (content[index + 1] & 0xFF) << 16
                | (content[index + 2] & 0xFF) << 8
                | (content[index + 3] & 0xFF);
    }

    public void int2Byte(byte[] content, int index, int value) {
        content[index] = (byte) ((value >> 24) & 0xFF);
        content[index + 1] = (byte) ((value >> 16) & 0xFF);
        content[index + 2] = (byte) ((value >> 8) & 0xFF);
        content[index + 3] = (byte) (value & 0xFF);
    }

}
