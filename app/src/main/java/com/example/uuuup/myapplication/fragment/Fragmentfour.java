package com.example.uuuup.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.uuuup.myapplication.LoginActivity;
import com.example.uuuup.myapplication.MainActivity;
import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.default_bus_Activity;
import com.example.uuuup.myapplication.entity.item_view;
import com.example.uuuup.myapplication.firstActivity;
import com.example.uuuup.myapplication.utils.DefaultSettingActivity;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class Fragmentfour extends Fragment {


    private ImageView blurImageView;
    private ImageView avatarImageView;

    public static Fragmentfour newInstance(){
        Fragmentfour fragment = new Fragmentfour();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentfour,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initview();
    }

    public void initview() {
        blurImageView = (ImageView) getView().findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) getView().findViewById(R.id.iv_avatar);

        Glide.with(getActivity()).load(R.drawable.zhihu)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);

        Glide.with(getActivity()).load(R.drawable.zhihu)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(avatarImageView);
        final item_view default_bus = (item_view) getView().findViewById(R.id.fragmentfour_default_bus);
        default_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), default_bus_Activity.class);
                startActivity(intent);
            }
        });

        final item_view sign_in = (item_view) getView().findViewById(R.id.fragmentfour_signin);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), firstActivity.class);
                startActivity(intent);
            }
        });
    }
}
