package com.example.uuuup.myapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.uuuup.myapplication.entity.item_bus;

import java.util.ArrayList;
import java.util.List;
import com.example.uuuup.myapplication.entity.busAdapt;
public class default_bus_Activity extends AppCompatActivity {
    private List<item_bus> msgList = new ArrayList<item_bus>();
    private RecyclerView msgRecyclerView;
    private busAdapt adapter;

    private SharedPreferences pref;//本地存储数据
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_bus_);
        initMsgs();
        msgRecyclerView = (RecyclerView) findViewById(R.id.bus_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        adapter = new busAdapt(msgList, getApplicationContext());
        msgRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initMsgs() {
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean bus1 = pref.getBoolean("1路", false);
        item_bus msg1 = new item_bus("1路", bus1);
        msgList.add(msg1);
        boolean bus2 = pref.getBoolean("1路", false);
        item_bus msg2 = new item_bus("2路", bus2);
        msgList.add(msg2);
        item_bus msg3 = new item_bus("3路", pref.getBoolean("3路",false));
        msgList.add(msg3);
        item_bus msg4 = new item_bus("4路", pref.getBoolean("4路",false));
        msgList.add(msg4);
        item_bus msg5 = new item_bus("5路", pref.getBoolean("5路",false));
        msgList.add(msg5);
        item_bus msg6 = new item_bus("6路", pref.getBoolean("6路",false));
        msgList.add(msg6);
        item_bus msg7 = new item_bus("7路", pref.getBoolean("7路",false));
        msgList.add(msg7);
        item_bus msg8 = new item_bus("8路", pref.getBoolean("8路",true));
        msgList.add(msg8);
        item_bus msg9 = new item_bus("9路", pref.getBoolean("9路",false));
        msgList.add(msg9);
        item_bus msg10 = new item_bus("10路", pref.getBoolean("10路",true));
        msgList.add(msg10);

    }
}
