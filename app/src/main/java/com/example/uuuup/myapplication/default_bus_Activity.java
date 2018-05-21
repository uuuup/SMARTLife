package com.example.uuuup.myapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        item_bus msg1 = new item_bus("1路", true);
        msgList.add(msg1);
        item_bus msg2 = new item_bus("2路", true);
        msgList.add(msg2);
        item_bus msg3 = new item_bus("3路", false);
        msgList.add(msg3);
        item_bus msg4 = new item_bus("4路", true);
        msgList.add(msg4);
        item_bus msg5 = new item_bus("5路", false);
        msgList.add(msg5);
        item_bus msg6 = new item_bus("6路", true);
        msgList.add(msg6);
        item_bus msg7 = new item_bus("7路", false);
        msgList.add(msg7);
        item_bus msg8 = new item_bus("8路", true);
        msgList.add(msg8);
        item_bus msg9 = new item_bus("9路", true);
        msgList.add(msg9);


        msgRecyclerView = (RecyclerView) findViewById(R.id.bus_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        adapter = new busAdapt(msgList);
        msgRecyclerView.setAdapter(adapter);

        editor= pref.edit();

        Button button = (Button) findViewById(R.id.set_default_bus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String default_bus = "";
                for (item_bus item : msgList){
                    if (item.getCheck() == true){
                        default_bus = default_bus + item.getBus_name() +"$1" + "#";
                    }else {
                        default_bus = default_bus + item.getBus_name() +"$0" + "#";
                    }
                }


                editor.putString("default_bus", default_bus);
                editor.commit();
                //String default_bus1 = pref.getString("default_bus", "");
                //Toast.makeText(getApplicationContext(), default_bus1, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initMsgs() {

    }
}
