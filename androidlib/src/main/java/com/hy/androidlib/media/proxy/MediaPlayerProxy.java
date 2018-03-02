package com.hy.androidlib.media.proxy;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.hy.androidlib.bean.http.HttpRequest;
import com.hy.androidlib.bean.http.HttpResponse;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.hy.androidlib.media.proxy.MediaPlayerProxy.ProxyListener.CREATE_LOCAL_SERVER_FAIL;
import static com.hy.androidlib.media.proxy.MediaPlayerProxy.ProxyListener.LOCAL_SOCKET_WAIT_TIMEOUT;
import static com.hy.androidlib.media.proxy.MediaPlayerProxy.ProxyListener.REMOTE_SOCKET_WAIT_TIMEOUT;
import static com.hy.androidlib.media.proxy.MediaPlayerProxy.ProxyListener.SOMETHING_ERROR;


/**
 * MediaPlayer本地Socket代理，完成播放歌曲缓存。
 * 简单的使用例子如下：
 * <pre>{@code
 * MediaPlayerProxy proxy = new MediaPlayerProxy.Builder()
 *      .cacheDirectory(new File(Environment.getExternalStorageDirectory(),
 *          "myDownload/"))
 *      .build();}</pre>
 * <p>
 * 典型的使用例子如下：
 * <pre>{@code
 * MediaPlayerProxy proxy = new MediaPlayerProxy.Builder()
 *      .connectTimeout(10, TimeUnit.SECONDS)
 *      .cacheDirectory(new File(Environment.getExternalStorageDirectory(),
 *          "myDownload/"))
 *      .setOnProxyListener(new MediaPlayerProxy.ProxyListener() {
 *          public void onError(int code, String msg) {
 *              Log.e(TAG + id, "main " + code + " " + msg);
 *          }
 *      })
 *      .build();}</pre>
 * <p>
 * 然后获取到代理的url地址。
 * <pre>{@code
 * String proxyUrl = proxy.startProxyUrl("http://fdfs.xmcdn.com/1.mp3");
 * mediaPlayer.setDataSource(context, Uri.parse(proxyUrl));
 * }</pre>
 * <p>
 * 可以查看某个Url的歌曲是否缓存。
 * <pre>{@code
 * boolean isCache = MediaPlayerProxy.isCache("http://fdfs.xmcdn.com/1.mp3");
 * }</pre>
 * 注意事项:
 * <ol>
 * <li>{@link MediaPlayerProxy}对象只能使用一次。</li>
 * <li>{@link #startProxyUrl(String)}只能调用一次，返回的url只能装填给MediaPlayer一次。</li>
 * <li>当该url已经下载过，{@link MediaPlayerProxy}会从磁盘缓存中输数据给MediaPlayer，用户只需要
 * {@link android.media.MediaPlayer#setDataSource(Context, Uri)}既可。</li>
 * </ol>
 * <p>
 * 功能
 * <ol>
 * <li>http协议网络歌曲资源缓存入磁盘。</li>
 * <li>提供断点下载功能，断点缓存。</li>
 * </ol>
 *
 * <b>远端http请求使用原始tcp实现，http重定向的资源无法读取。但是不需要引入okhttp包，更加轻量级。</b>
 *
 * @author hy 2018/2/5
 */
public class MediaPlayerProxy {
    public static final String TAG = "@Proxy";

    // 缓存路径，只可设置一次，不可修改。
    private static File cacheDirectory;
    // 磁盘缓存器。
    private static DiskCache diskCache;

    private static int idPool = 1;

    // 该代理工具id。
    private int id;

    // 本地监听。
    private ServerSocket localServerSocket;
    private int localServerPort = -1;
    private Socket mediaPlayerSocket;
    private String remoteHost;
    private int remotePort;

    // 远端真实服务器。
    private Socket remoteSocket;

    // 读取缓存。本地通信使用buffer1，远程使用buffer2。
    private byte[] buffer1 = new byte[1024];
    private int len1;
    private byte[] buffer2 = new byte[1024];
    private int len2;

    // 4个流。
    private InputStream localIs;
    private OutputStream localOs;
    private InputStream remoteIs;
    private OutputStream remoteOs;

    // builder参数。
    private Builder builder;

    // url。
    private String rawUrl;
    private String proxyUrl;

    // 线程。
    private Thread localServerThread;
    private Thread accessDataThread;

    // 等待对方流打开成功的信号量。
    private CountDownLatch openStreamSignal;
    // 本地socket发送了http请求后的信号量。
    private CountDownLatch httpRequestSignal;
    // 为了适应断点下载，增加本地thread是否需要写到远程输出流方法。
    private AtomicBoolean isNeedWriteRemoteOs;

    // 是否已经启动代理。
    private boolean isStarted;

    /**
     * 设置歌曲缓存的目录。只可设置一次，不可修改。
     *
     * @param cacheDirectory
     * @return
     */
    public static void cacheDirectory(File cacheDirectory) {
        if (MediaPlayerProxy.cacheDirectory != null) {
            throw new RuntimeException("cacheDirectory has been set to "
                    + MediaPlayerProxy.cacheDirectory.getAbsolutePath());
        }
        if (cacheDirectory == null) {
            throw new RuntimeException("cacheDirectory can not be null");
        }
        MediaPlayerProxy.cacheDirectory = cacheDirectory;
        MediaPlayerProxy.diskCache = new DiskCache(cacheDirectory);
    }

    /**
     * 该URl的歌曲是否缓存下磁盘。
     *
     * @param url
     * @return
     */
    public static boolean isCache(String url) {
        if (diskCache == null) {
            throw new RuntimeException("please call cacheDirectory fisrt!");
        }
        return diskCache.getCacheState(url) == DiskCache.FULL_CACHE;
    }

    private MediaPlayerProxy(Builder builder) {
        this.id = idPool++;
        if (idPool == Integer.MAX_VALUE) {
            idPool = 1;
        }

        this.builder = builder;
        openStreamSignal = new CountDownLatch(2);
        httpRequestSignal = new CountDownLatch(1);
        isNeedWriteRemoteOs = new AtomicBoolean(true);
        if (cacheDirectory == null) {
            throw new RuntimeException("cacheDirectory can not be null");
        }
        try {
            createRandomPortLocalServer();
        } catch (Exception e) {
            return;
        }
        localServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                localServerThreadAction();
            }
        });
        localServerThread.start();
    }

    // 多次尝试打开本地监听端口。
    private void createRandomPortLocalServer() throws Exception {
        Random random = new Random();
        int startPort = 39000;
        int port = random.nextInt(65535 - startPort) + startPort;
        boolean isQuit = false;
        int tryCnt = 0;
        int maxTryCnt = 100;
        while (!isQuit) {
            try {
                localServerSocket = new ServerSocket(port);
            } catch (BindException e) {
                port = random.nextInt(65535 - startPort) + startPort;
                tryCnt++;
                if (tryCnt >= maxTryCnt) {
                    if (builder.listener != null) {
                        builder.listener.onError(CREATE_LOCAL_SERVER_FAIL, "create local server fail");
                    }
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                if (builder.listener != null) {
                    builder.listener.onError(CREATE_LOCAL_SERVER_FAIL, "create local server fail");
                }
                throw new RuntimeException();
            }
            isQuit = true;
        }
        localServerPort = port;
        Log.i(TAG + id, "local socket listen port: " + localServerPort);
    }

    /**
     * 从原始链接获取到代理链接，并开始代理，连接到远端服务器。
     * 如果链接不是http连接而是本地链接，则直接返回，不开线程。
     *
     * @param rawUrl
     * @return
     */
    public String startProxyUrl(final String rawUrl) {
        if (isStarted) {
            throw new RuntimeException("has been started");
        }
        isStarted = true;

        this.rawUrl = rawUrl;

        // 判断是否是本地Uri。
        if (!rawUrl.startsWith("http:")) {
            return rawUrl;
        }

        // 替换Uri，生成代理url。
        URI uri = URI.create(rawUrl);
        remotePort = uri.getPort();
        remoteHost = uri.getHost();

        // 找不到主机。
        if (remoteHost == null) {
            return rawUrl;
        }

        String localAddress = "127.0.0.1:" + localServerPort;
        if (remotePort == -1) {
            proxyUrl = rawUrl.replace(remoteHost, localAddress);
            remotePort = 80;
        } else {
            proxyUrl = rawUrl.replace(remoteHost + ":" + remotePort, localAddress);
        }

        accessDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 查缓存状态。
                int state = diskCache.getCacheState(rawUrl);
                if (state == DiskCache.NO_CACHE) {
                    isNeedWriteRemoteOs.set(true);
                    Log.i(TAG + id, "get music data from network.");
                    accessDataThreadNetWorkAction();
                } else if (state == DiskCache.FULL_CACHE) {
                    isNeedWriteRemoteOs.set(false);
                    Log.i(TAG + id, "get music data from disk cache.");
                    accessDataThreadLocalFileAction();
                } else if (state == DiskCache.CACHING) {
                    // 开放断点下载。
                    isNeedWriteRemoteOs.set(false);
                    Log.i(TAG + id, "get music data from network breakpoint download.");
                    accessDataThreadBreakpointAction();

//                    accessDataThreadNetWorkAction();
                }
            }
        });
        accessDataThread.start();

        Log.i(TAG + id, "start proxy. local port: " + localServerPort + ", " +
                "remote url: " + remoteHost + ":" + remotePort);
        return proxyUrl;
    }

    @WorkerThread
    private void localServerThreadAction() {
        try {
            mediaPlayerSocket = localServerSocket.accept();
            localIs = mediaPlayerSocket.getInputStream();
            localOs = mediaPlayerSocket.getOutputStream();

            // 通知对方自己连接完毕，并等待对方。等待远端超时时间。
            openStreamSignal.countDown();
            boolean hasWait = openStreamSignal.await(builder.connectTimeout, builder.timeUnit);
            if (!hasWait) {
                if (builder.listener != null) {
                    builder.listener.onError(REMOTE_SOCKET_WAIT_TIMEOUT, "wait remote stream time out");
                }
                return;
            }

            boolean hasCutHead = false;
            StringBuilder hsb = new StringBuilder();

            while ((len1 = localIs.read(buffer1)) != -1) {
                // 修改http请求头。
                if (hasCutHead) {
                    if (isNeedWriteRemoteOs.get()) {
                        remoteOs.write(buffer1, 0, len1);
                    }
                } else {
                    String str = new String(buffer1, 0, len1);
                    hsb.append(str);
                    int index = hsb.toString().indexOf("\r\n\r\n");
                    if (index == -1) {
                        continue;
                    }
                    hasCutHead = true;
                    String rawHttpHead = hsb.toString().substring(0, index + 4);
                    HttpRequest httpRequest = HttpRequest.parseString(rawHttpHead);

                    Log.i(TAG + id, "localServerThreadAction media player http request.\n" + httpRequest.toString());

                    //把request中的本地ip改为远程ip
                    httpRequest.Host = remoteHost + ":" + remotePort;

                    if (isNeedWriteRemoteOs.get()) {
                        remoteOs.write(httpRequest.toString().getBytes());
                    }
                    httpRequestSignal.countDown();
                    if (isNeedWriteRemoteOs.get()) {
                        remoteOs.write(buffer1, index + 4, len1 - index - 4);
                    }
                }
            }

            Log.i(TAG + id, "exit local");
        } catch (SocketException e) {
            Log.e(TAG + id, "localServerThreadAction SocketException", e);
        } catch (Exception e) {
            if (builder.listener != null) {
                builder.listener.onError(SOMETHING_ERROR, e.getMessage());
            }
            Log.e(TAG + id, "localServerThreadAction", e);
        } finally {
            Log.i(TAG + id, "close local");
            close(localIs);
            close(localOs);
            close(mediaPlayerSocket);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                close(localServerSocket);
            }

            // 手动关闭远程socket，只需关闭inputStream触发本地线程异常。
            close(remoteIs);
        }
    }

    @WorkerThread
    private void accessDataThreadNetWorkAction() {
        try {
            SocketAddress remoteAddress = new InetSocketAddress(remoteHost, remotePort);
            remoteSocket = new Socket();
            remoteSocket.connect(remoteAddress, 5000);
            remoteIs = remoteSocket.getInputStream();
            remoteOs = remoteSocket.getOutputStream();

            // 通知对方自己连接完毕，并等待对方。等待本地超时时间。
            openStreamSignal.countDown();
            boolean hasWait = openStreamSignal.await(builder.connectTimeout, builder.timeUnit);
            if (!hasWait) {
                if (builder.listener != null) {
                    builder.listener.onError(LOCAL_SOCKET_WAIT_TIMEOUT, "wait local stream time out");
                }
                return;
            }

            Log.i(TAG + id, "connect " + rawUrl + " success");

            // 生成缓存文件处理者。
            final FileHandler fileHandler = new FileHandler(diskCache.generateCacheFileFromUrl(rawUrl).getAbsolutePath());

            boolean hasCutHead = false;
            StringBuilder hsb = new StringBuilder();

            while ((len2 = remoteIs.read(buffer2)) != -1) {
                // 去掉http响应头，剩下的数据交给文件处理者处理。
                if (hasCutHead) {
                    fileHandler.write(buffer2, 0, len2);
                    if (builder.dataHandler != null) {
                        builder.dataHandler.write(buffer2, 0, len2);
                    }
                } else {
                    String str = new String(buffer2, 0, len2);
                    hsb.append(str);
                    int index = hsb.toString().indexOf("\r\n\r\n");
                    if (index == -1) {
                        continue;
                    }
                    hasCutHead = true;
                    String rawHttpHead = hsb.toString().substring(0, index + 4);
                    HttpResponse httpResponse = HttpResponse.parseString(rawHttpHead);

                    Log.i(TAG + id, "accessDataThreadNetWorkAction remote http response.\n" + httpResponse.toString());

                    // 写入文件开头，自定义文件头。
                    MusicHead musicHead = new MusicHead();
                    musicHead.contentLength = Integer.parseInt(httpResponse.ContentLength);
                    musicHead.ETag = httpResponse.ETag == null ? "\"123\"" : httpResponse.ETag;

                    fileHandler.start();
                    musicHead.writeToOutputStream(new OutputStream() {
                        @Override
                        public void write(int b) throws IOException {
                            fileHandler.raf.write(b);
                        }
                    });

                    fileHandler.write(buffer2, index + 4, len2 - index - 4);
                    if (builder.dataHandler != null) {
                        builder.dataHandler.start();
                        builder.dataHandler.write(buffer2, index + 4, len2 - index - 4);
                    }
                }

                localOs.write(buffer2, 0, len2);
            }

            fileHandler.end();
            if (builder.dataHandler != null) {
                builder.dataHandler.end();
            }

            Log.i(TAG + id, "exit remote network");
        } catch (SocketException e) {
            Log.e(TAG + id, "accessDataThreadNetWorkAction SocketException", e);
        } catch (Exception e) {
            if (builder.listener != null) {
                builder.listener.onError(SOMETHING_ERROR, e.getMessage());
            }
            Log.e(TAG + id, "accessDataThreadNetWorkAction", e);
        } finally {
            Log.i(TAG + id, "close remote network");
            close(remoteOs);
            close(remoteIs);
            close(remoteSocket);

            // 手动关闭本地socket，只需关闭inputStream触发本地线程异常。
            close(localIs);
        }
    }

    @WorkerThread
    private void accessDataThreadLocalFileAction() {
        // 寻找缓存文件。
        File file = diskCache.generateCacheFileFromUrl(rawUrl);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);

            // 通知对方自己连接完毕，并等待对方。等待本地超时时间。
            openStreamSignal.countDown();
            boolean hasWait = openStreamSignal.await(builder.connectTimeout, builder.timeUnit);
            if (!hasWait) {
                if (builder.listener != null) {
                    builder.listener.onError(LOCAL_SOCKET_WAIT_TIMEOUT, "wait local stream time out");
                }
                return;
            }

            // 读出自定义文件头。
            MusicHead musicHead = MusicHead.readFromInputStream(fis);

            long size = musicHead.contentLength;
            SimpleDateFormat sdf = new SimpleDateFormat("E, ww MMM yyyy HH:mm:ss z", Locale.US);
            String date = sdf.format(new Date());

            // 写入http响应头。
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.head = "HTTP/1.1 200 OK";
            httpResponse.Date = date;
            httpResponse.Server = "MediaPlayerProxy.java";
            httpResponse.LastModified = date;
            httpResponse.ETag = "\"9c38e0-55c1b887821c0\"";
            httpResponse.AcceptRanges = "bytes";
            httpResponse.ContentLength = size + "";
            httpResponse.KeepAlive = "timeout=5, max=100";
            httpResponse.Connection = "Keep-Alive";
            httpResponse.ContentType = "audio/mpeg";

            Log.i(TAG + id, "accessDataThreadLocalFileAction fake http response.\n" + httpResponse.toString());

            // 等待localSocket已经发送请求头。
            httpRequestSignal.await();
            localOs.write(httpResponse.toString().getBytes());

            // 本地文件写入。
            if (builder.dataHandler != null) {
                builder.dataHandler.start();
            }
            while ((len2 = fis.read(buffer2)) != -1) {
                if (builder.dataHandler != null) {
                    builder.dataHandler.write(buffer2, 0, len2);
                }
                localOs.write(buffer2, 0, len2);
            }
            if (builder.dataHandler != null) {
                builder.dataHandler.end();
            }

            Log.i(TAG + id, "end write local file");
        } catch (SocketException e) {
            Log.e(TAG + id, "accessDataThreadLocalFileAction SocketException", e);
        } catch (Exception e) {
            if (builder.listener != null) {
                builder.listener.onError(SOMETHING_ERROR, e.getMessage());
            }
            Log.e(TAG + id, "accessDataThreadLocalFileAction", e);
        } finally {
            Log.i(TAG + id, "close write");
            close(fis);
            close(remoteIs);
            close(remoteOs);

            // 手动关闭本地socket，只需关闭inputStream触发本地线程异常。
            close(localIs);
        }
    }

    @WorkerThread
    private void accessDataThreadBreakpointAction() {
        // 寻找缓存文件。
        File file = diskCache.generateCacheFileFromUrl(rawUrl);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);

            SocketAddress remoteAddress = new InetSocketAddress(remoteHost, remotePort);
            remoteSocket = new Socket();
            remoteSocket.connect(remoteAddress, 5000);

            remoteIs = remoteSocket.getInputStream();
            remoteOs = remoteSocket.getOutputStream();

            Log.i(TAG + id, "connect " + rawUrl + " success");

            // 通知对方自己连接完毕，并等待对方。等待本地超时时间。
            openStreamSignal.countDown();
            boolean hasWait = openStreamSignal.await(builder.connectTimeout, builder.timeUnit);
            if (!hasWait) {
                if (builder.listener != null) {
                    builder.listener.onError(LOCAL_SOCKET_WAIT_TIMEOUT, "wait local stream time out");
                }
                return;
            }

            // 读出自定义文件头。
            MusicHead musicHead = MusicHead.readFromInputStream(fis);

            long size = musicHead.contentLength;
            SimpleDateFormat sdf = new SimpleDateFormat("E, ww MMM yyyy HH:mm:ss z", Locale.US);
            String date = sdf.format(new Date());

            // 写入http响应头。
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.head = "HTTP/1.1 200 OK";
            httpResponse.Date = date;
            httpResponse.Server = "MediaPlayerProxy.java";
            httpResponse.LastModified = date;
            httpResponse.ETag = "\"9c38e0-55c1b887821c0\"";
            httpResponse.AcceptRanges = "bytes";
            httpResponse.ContentLength = size + "";
            httpResponse.KeepAlive = "timeout=5, max=100";
            httpResponse.Connection = "Keep-Alive";
            httpResponse.ContentType = "audio/mpeg";

            // 等待localSocket已经发送请求头。
            httpRequestSignal.await();
            Log.i(TAG + id, "accessDataThreadBreakpointAction fake http response.\n" + httpResponse.toString());
            localOs.write(httpResponse.toString().getBytes());

            // 本地文件写入。写入的同时记录写入了多少个字节，以备后续使用。此处读完后不关闭fis。
            if (builder.dataHandler != null) {
                builder.dataHandler.start();
            }
            long hasCacheSize = 0;
            while ((len2 = fis.read(buffer2)) != -1) {
                hasCacheSize += len2;
                if (builder.dataHandler != null) {
                    builder.dataHandler.write(buffer2, 0, len2);
                }
                localOs.write(buffer2, 0, len2);
            }

            Log.i(TAG + id, "end write local cache");

            // 生成缓存文件处理者。
            FileHandler fileHandler = new FileHandler(diskCache.generateCacheFileFromUrl(rawUrl).getAbsolutePath());

            boolean hasCutHead = false;
            StringBuilder hsb = new StringBuilder();

            // 发送断点下载http请求。
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.head = "GET " + URI.create(rawUrl).getPath() + " HTTP/1.1";
            httpRequest.AcceptEncoding = "gzip";
            httpRequest.Connection = "Keep-Alive";
            httpRequest.Host = remoteHost + ":" + remotePort;
            httpRequest.UserAgent = "MediaPlayerProxy.java";
            httpRequest.IfRange = null;
            httpRequest.Range = "bytes=" + (hasCacheSize + 1) + "-";

            Log.i(TAG + id, "accessDataThreadBreakpointAction http breakpoint download request.\n" + httpRequest.toString());

            remoteOs.write(httpRequest.toString().getBytes());

            // 解析回应的Http响应。
            while ((len2 = remoteIs.read(buffer2)) != -1) {
                // 去掉http响应头，剩下的数据交给文件处理者处理。
                if (hasCutHead) {
                    fileHandler.write(buffer2, 0, len2);
                    if (builder.dataHandler != null) {
                        builder.dataHandler.write(buffer2, 0, len2);
                    }
                    localOs.write(buffer2, 0, len2);
                } else {
                    String str = new String(buffer2, 0, len2);
                    hsb.append(str);
                    int index = hsb.toString().indexOf("\r\n\r\n");
                    if (index == -1) {
                        continue;
                    }
                    hasCutHead = true;
                    String rawHttpHead = hsb.toString().substring(0, index + 4);
                    HttpResponse httpResponse2 = HttpResponse.parseString(rawHttpHead);

                    Log.i(TAG + id, "accessDataThreadBreakpointAction remote http response.\n" + httpResponse2.toString());

                    // http响应错误。
                    if (httpResponse2.responseCode != 206) {
                        Log.e(TAG + id, "remote http response code is not 206");
                        return;
                    }

                    fileHandler.start();
                    fileHandler.accessToPosition(musicHead.getHeadLen() + hasCacheSize);

                    fileHandler.write(buffer2, index + 4, len2 - index - 4);
                    if (builder.dataHandler != null) {
                        builder.dataHandler.start();
                        builder.dataHandler.write(buffer2, index + 4, len2 - index - 4);
                    }

                    // 此处本地socket不写入httpResponse头。
                    localOs.write(buffer2, index + 4, len2 - index - 4);
                }
            }

            fileHandler.end();
            if (builder.dataHandler != null) {
                builder.dataHandler.end();
            }

            Log.i(TAG + id, "exit remote");

        } catch (SocketException e) {
            Log.e(TAG + id, "accessDataThreadLocalFileAction SocketException", e);
        } catch (Exception e) {
            if (builder.listener != null) {
                builder.listener.onError(SOMETHING_ERROR, e.getMessage());
            }
            Log.e(TAG + id, "accessDataThreadLocalFileAction", e);
        } finally {
            Log.i(TAG + id, "close write");
            close(fis);
            close(remoteIs);
            close(remoteOs);
        }

    }

    /**
     * Builder生成代理对象。一个代理对象只能使用一次。
     */
    public static class Builder {

        ProxyListener listener;
        DataHandler dataHandler;
        int connectTimeout = 10;// s
        TimeUnit timeUnit = TimeUnit.SECONDS;

        /**
         * 设置数据处理者。
         *
         * @param dataHandler
         * @return
         */
        public Builder setDataHandler(DataHandler dataHandler) {
            this.dataHandler = dataHandler;
            return this;
        }

        /**
         * 设置错误监听器。
         *
         * @param listener
         * @return
         */
        public Builder setOnProxyListener(ProxyListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置连接超时时间。
         *
         * @param timeout
         * @param timeUnit
         * @return
         */
        public Builder connectTimeout(int timeout, TimeUnit timeUnit) {
            this.connectTimeout = timeout;
            this.timeUnit = timeUnit;
            return this;
        }

        /**
         * 构造{@link MediaPlayerProxy}。
         *
         * @return
         */
        public MediaPlayerProxy build() {
            return new MediaPlayerProxy(this);
        }
    }

    /**
     * 监听器，监听代理过程中发生的错误。
     */
    public interface ProxyListener {

        /**
         * 发生了一些未知错误。
         */
        int SOMETHING_ERROR = -1;

        /**
         * 成功。
         */
        int SUCCESS = 0;

        /**
         * 创建本地Socket服务监听失败。
         */
        int CREATE_LOCAL_SERVER_FAIL = 1;

        /**
         * 等待远程网络连接超时。
         */
        int REMOTE_SOCKET_WAIT_TIMEOUT = 2;

        /**
         * 等待本地Socket创建连接超时。
         */
        int LOCAL_SOCKET_WAIT_TIMEOUT = 3;

        /**
         * 发生了一些错误。
         *
         * @param code 错误代码 {@link #REMOTE_SOCKET_WAIT_TIMEOUT}
         * @param msg  错误信息。
         */
        void onError(int code, String msg);
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
