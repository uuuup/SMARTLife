package com.example.uuuup.myapplication.entity;

/**
 * Created by uuuup on 2018/5/19.
 */

public class item_bus {
    private String bus_name;
    private boolean check;
    public  item_bus(String str1, boolean check1){
        bus_name = str1;
        check = check1;
    }
    public String getBus_name(){
        return bus_name;
    }
    public boolean getCheck(){
        return check;
    }
    public void setBus_name(String string){
        bus_name = string;
    }
    public void setCheck(boolean check1){
        check = check1;
    }
}
