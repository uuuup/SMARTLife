package com.example.uuuup.myapplication;

/**
 * Created by uuuup on 2018/4/7.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;

import com.example.uuuup.myapplication.base.BaseApplication;
import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.utils.NavigationUtils;
import com.example.uuuup.myapplication.utils.PhoneCallUtils;

public class InfoWinAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {
        private Context mContext = BaseApplication.getIntance().getBaseContext();
        private LatLng latLng;
        private LinearLayout call;
        private LinearLayout navigation;
        private TextView nameTV;
        private String Name;
        private TextView addrTV;
        private String snippet;

    @Override
    public View getInfoWindow(Marker marker) {
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        Name = marker.getTitle();
        snippet = marker.getSnippet();

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        navigation = (LinearLayout) view.findViewById(R.id.navigation_LL);
        call = (LinearLayout) view.findViewById(R.id.call_LL);
        nameTV = (TextView) view.findViewById(R.id.agent_name);
        addrTV = (TextView) view.findViewById(R.id.agent_addr);

        nameTV.setText(Name);
        addrTV.setText(snippet);
        navigation.setOnClickListener(this);
        call.setOnClickListener(this);
        return view;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        Name = marker.getTitle();
        snippet = marker.getSnippet();
    }

    @NonNull
    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        navigation = (LinearLayout) view.findViewById(R.id.navigation_LL);
        call = (LinearLayout) view.findViewById(R.id.call_LL);
        nameTV = (TextView) view.findViewById(R.id.agent_name);
        addrTV = (TextView) view.findViewById(R.id.agent_addr);

        nameTV.setText(Name);
        addrTV.setText(snippet);
        //addrTV.setText(String.format(mContext.getString(R.string.agent_addr),snippet));

        navigation.setOnClickListener(this);
        call.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.navigation_LL:  //点击导航
                NavigationUtils.Navigation(latLng);
                break;

            case R.id.call_LL:  //点击打电话
                PhoneCallUtils.call("028-"); //TODO 处理电话号码
                break;
        }
    }
}
