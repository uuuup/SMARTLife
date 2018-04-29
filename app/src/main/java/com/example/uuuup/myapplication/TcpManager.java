package com.example.uuuup.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TcpManager extends Thread {
    private String sendData;
    private String receiveData;
    private Socket socket;

    public TcpManager(String string)
    {
        System.out.print("socket data: " + string);
        this.sendData = string;
    }

    public String getData(){
        return this.receiveData;
    }

    @Override
    public void run() {
        // 执行完毕后给handler发送一个空消息
        try {
            // 实例化Socket
            socket = new Socket("211.159.187.82", 20005);
            socket.setSoTimeout(10000);
            System.out.print("new socket!!!!!!!!!");

            //获取输入流
            BufferedReader buf =  new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 获得输出流
            PrintStream out = new PrintStream(socket.getOutputStream());

            out.print(this.sendData);
            try {
                this.receiveData = buf.readLine();
                System.out.println("receive data"+ this.receiveData);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Time out, No response");
            }
            buf.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //handler.sendEmptyMessage(0); //不需要界面变化的话可不写handler
    }
}
