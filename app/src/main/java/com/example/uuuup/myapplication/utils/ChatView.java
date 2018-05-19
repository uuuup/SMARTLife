package com.example.uuuup.myapplication.utils;

/**
 * Created by uuuup on 2018/5/19.
 */

import com.example.uuuup.myapplication.utils.ChatBean;

public interface ChatView {
    public String getUserId();
    public void showDiaolg(String msg);
    public void receiveMsg(ChatBean bean);
}