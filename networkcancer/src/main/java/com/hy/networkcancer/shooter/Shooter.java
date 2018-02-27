package com.hy.networkcancer.shooter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 网络发射器。
 *
 * @author hy 2018/2/27
 */
public class Shooter {

    // 线程池。
    private int sockets;
    private int sleepTime;
    private int port;
    private ExecutorService pool;
    private byte[] data;
    private LogListener listener;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * 创建发射器。
     *
     * @param sockets    打开的socket数。
     * @param packetSize 每次写入的数据大小。
     */
    public Shooter(int sockets, int packetSize, int port, int sleepTime) {
        if (sockets < 1 || sockets > 100) {
            throw new IllegalArgumentException("threads is " + sockets);
        }
        if (packetSize < 1 || packetSize > 10 * 1024) {
            throw new IllegalArgumentException("packet is " + packetSize);
        }
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("port is " + port);
        }
        if (sleepTime < 0) {
            throw new IllegalArgumentException("sleep time is " + packetSize);
        }
        this.sockets = sockets;
        this.sleepTime = sleepTime;
        this.port = port;
        pool = Executors.newFixedThreadPool(sockets);
        data = new byte[packetSize];
        byte[] bs = "hello, friend.".getBytes();
        for (int i = 0; i < packetSize; i++) {
            data[i] = bs[i % bs.length];
        }
    }

    /**
     * 设置信息输出监听。
     *
     * @param listener
     */
    public void setLogListener(LogListener listener) {
        this.listener = listener;
    }

    /**
     * 开始发射。
     */
    public void startShoot() {
        for (int i = 0; i < sockets; i++) {
            pool.submit(new Run());
        }
        isRunning.set(true);
    }

    /**
     * 停止发射。
     */
    public void stopShoot() {
        pool.shutdownNow();
        isRunning.set(false);
    }

    public class Run implements Runnable {
        @Override
        public void run() {
            String id = Thread.currentThread().getName();
            listener.log("udp", id + " start shoot");

            long lastTime = 0;
            final int MAX_TIMES_DURATION = 2000;

            try {
                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName("255.255.255.255");
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

                while (isRunning.get()) {
                    socket.send(packet);

                    if (System.currentTimeMillis() - lastTime > MAX_TIMES_DURATION) {
                        listener.log("udp", id + "send udp success ");
                        lastTime = System.currentTimeMillis();
                    }
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                listener.log("udp", id + "stop. " + e.getMessage());
            } catch (Exception e) {
                listener.log("udp", id + "fail. " + e.getMessage());
            }
        }
    }

    public interface LogListener {
        void log(String who, String message);
    }
}
