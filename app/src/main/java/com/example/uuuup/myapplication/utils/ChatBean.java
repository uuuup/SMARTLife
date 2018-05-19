package com.example.uuuup.myapplication.utils;

/**
 * Created by uuuup on 2018/5/19.
 */

public class ChatBean {
    public String content;
    public String name;
    public long time;

    public ChatBean(String content, String name) {
        this.content = content;
        this.name = name;
        time = System.currentTimeMillis();
    }
}
