package com.example.uuuup.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class TcpManager {

    protected void onCreate(Bundle savedInstanceState) {
        try {

            Socket socket = new Socket("127.0.0.1", 22);
            //设置10秒之后即认为是超时
            socket.setSoTimeout(10000);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String line = br.readLine();

            br.close();
            socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            Log.e("UnknownHost", "来自服务器的数据");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException", "来自服务器的数据");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}