package com.example.uuuup.myapplication.utils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by uuuup on 2018/5/19.
 */

public class SocketThread extends Thread  {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private ChatView chatView;

    public SocketThread(ChatView chatView) {
        this.chatView = chatView;
    }

    private void init() {
        try {
            socket = new Socket("211.159.187.82", 9999);
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
            if (!socket.isOutputShutdown()) {
                ChatBean bean = new ChatBean("join", chatView.getUserId());
                out.println(new Gson().toJson(bean));
            }
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            mHandler.sendEmptyMessage(-1);
        } catch (IOException ex) {
            ex.printStackTrace();
            mHandler.sendEmptyMessage(0);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            mHandler.sendEmptyMessage(-2);
        }
    }

    public void sendMsg(String msg){
        if (!socket.isOutputShutdown()) {
            ChatBean bean = new ChatBean(msg, chatView.getUserId());
            out.println(new Gson().toJson(bean));
        }
    }

    //接收线程发送过来信息，并用TextView显示
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -2:
                    chatView.showDiaolg("IllegalArgumentException");
                    break;
                case -1:
                    chatView.showDiaolg("UnknownHostException");
                    break;
                case 0:
                    chatView.showDiaolg("IOException");
                    break;
                case 1:
                    String content = (String) msg.obj;
                    ChatBean bean = new Gson().fromJson(content, ChatBean.class);
                    chatView.receiveMsg(bean);
                    break;
            }
        }
    };

    @Override
    public void run() {
        super.run();
        init();
        try {
            while (true) {
                if (!socket.isClosed()) {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            String content;
                            if ((content = in.readLine()) != null) {
                                content += "\n";
                                Message message = mHandler.obtainMessage();
                                message.obj = content;
                                message.what = 1;
                                mHandler.sendMessage(message);
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
