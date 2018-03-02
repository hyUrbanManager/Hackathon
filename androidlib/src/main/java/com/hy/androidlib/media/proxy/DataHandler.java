package com.hy.androidlib.media.proxy;

/**
 * 数据处理者，用户可以插入自己的数据处理者自行处理数据。
 *
 * @author hy 2018/2/5
 */
public interface DataHandler {

    /**
     * 开始处理数据。意味着文件数据已到来，准备开始。
     */
    void start();

    /**
     * 有数据到来。
     *
     * @param bytes 数据字节数组
     * @param start 数据字节数组开始索引
     * @param len   数据字节数组长度
     */
    void write(byte[] bytes, int start, int len);

    /**
     * 数据接收完毕，准备退出。
     */
    void end();
}
