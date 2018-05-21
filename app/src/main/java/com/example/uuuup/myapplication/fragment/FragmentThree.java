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
import com.example.uuuup.myapplication.MainActivity;
import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.default_bus_Activity;
import com.example.uuuup.myapplication.entity.item_view;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FragmentThree extends Fragment {


    private ImageView blurImageView;
    private ImageView avatarImageView;

    public static FragmentThree newInstance(){
        FragmentThree fragment = new FragmentThree();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initview();
    }

    public void initview() {
        //getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        blurImageView = (ImageView) getView().findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) getView().findViewById(R.id.iv_avatar);

        Glide.with(getActivity()).load(R.drawable.zhihu)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);

        Glide.with(getActivity()).load(R.drawable.zhihu)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(avatarImageView);
        final item_view default_bus = (item_view) getView().findViewById(R.id.default_bus);
        default_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
