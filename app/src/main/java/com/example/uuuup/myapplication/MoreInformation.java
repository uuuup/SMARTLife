package com.example.uuuup.myapplication;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;

public class MoreInformation extends BaseActivity {

    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;
    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);

        //将传过的信息数组转化为字符串显示
        data = getIntent().getStringExtra("more_information").split(";");
        for (int i = 0 ; i < data.length; i++){
            timelineRowsList.add(createRandomTimelineRow(i,data[i]));
        }
        myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList, true);

        //Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) findViewById(R.id.timeline_listView1);
        myListView.setAdapter(myAdapter);

        //if you wish to handle list scrolling
        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount && this.currentScrollState == SCROLL_STATE_IDLE) {
                    for (int i = 0 ; i < data.length; i++){
                        timelineRowsList.add(createRandomTimelineRow(i,data[i]));
                    }
                }
            }
        });

        //if you wish to handle the clicks on the rows
        AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimelineRow row = timelineRowsList.get(position);
                //Toast.makeText(TextBusActivity.this, row.getTitle(), Toast.LENGTH_SHORT).show();
            }
        };
        myListView.setOnItemClickListener(adapterListener);
    }

    //Method to create new Timeline Row
    private TimelineRow createRandomTimelineRow(int id, String str) {
        TimelineRow myRow = new TimelineRow(id);
        myRow.setTitle("线路 " + str);
        myRow.setDescription("xxx分钟到站" );
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.bus));
        myRow.setBellowLineColor(2);
        //to set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(3);
        return myRow;
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return ;
    }
}
