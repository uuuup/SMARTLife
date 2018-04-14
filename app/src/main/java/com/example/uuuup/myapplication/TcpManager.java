package com.example.uuuup.myapplication;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by uuuup on 2018/4/14.
 */

public class TcpManager {
    protected static final int STATE_FROM_SERVER_OK = 0;
    private static String dsName = "127.0.0.1";
    private static int dstPort = 22;
    private static Socket socket;

    private static TcpManager instance;

    private TcpManager() {
    };

    public static TcpManager getInstance() {
        if (instance == null) {
            synchronized (TcpManager.class) {
                if (instance == null) {
                    instance = new TcpManager();
                }
            }
        }
        return instance;
    }

    /**
     * 连接
     *
     * @return
     */
    public boolean connect(final Handler handler) {

        if (socket == null || socket.isClosed()) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        socket = new Socket(dsName, dstPort);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException("连接错误: " + e.getMessage());
                    }

                    try {
                        // 输入流，为了获取客户端发送的数据
                        InputStream is = socket.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            final String result = new String(buffer, 0, len);

                            Message msg = Message.obtain();
                            msg.obj = result;
                            msg.what = STATE_FROM_SERVER_OK;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        return true;
    }

    /**
     * 发送信息
     *
     * @param content
     */
    public String sendMessage(String content) throws IOException {
        OutputStream os = null;
        BufferedReader buf = null;
        try {
            if (socket != null) {
                os = socket.getOutputStream();
                buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                os.write(content.getBytes());
                os.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("发送失败:" + e.getMessage());
        }
        return buf.readLine();
    }
    /**
     * 关闭连接
     */
    public void disConnect() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException("关闭异常:" + e.getMessage());
            }
            socket = null;
        }
    }
}