package com.example.uuuup.myapplication.chat.view;


import com.example.uuuup.myapplication.chat.bean.ChatBean;

/**
 * Created by Administrator on 2016/4/16 0016.
 */
public interface ChatView {
    public String getUserId();
    public void showDiaolg(String msg);
    public void receiveMsg(ChatBean bean);
}
