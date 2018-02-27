package com.hy.parser.http.server;

import org.junit.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP服务器。
 *
 * @author hy 2018/2/27
 */
public class UdpServer {

    @Test
    public void server() throws Exception {
        byte[] bytes = new byte[1024 * 2];
        DatagramSocket socket = new DatagramSocket(8990);
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

        while (true) {
            socket.receive(packet);
            System.out.println(System.currentTimeMillis() + " : receive");
        }
    }

}
