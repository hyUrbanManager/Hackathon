package com.hy.parser.socket;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2018/4/9.
 *
 * @author hy 2018/4/9
 */
public class TcpPortTest {

    @Test
    public void test1() {
        System.out.println(tcp("localhost", 8081));
        System.out.println(tcp("localhost", 8082));
    }

    public static boolean tcp(String address, int port) {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = new Socket(address, port);
            is = socket.getInputStream();
            os = socket.getOutputStream();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(is);
            close(os);
            close(socket);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
