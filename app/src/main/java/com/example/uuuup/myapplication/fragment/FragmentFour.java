package com.example.uuuup.myapplication.fragment;

import com.example.uuuup.myapplication.Constants;

import android.os.Bundle;

public class FragmentFour extends FragmentBase {

    public static FragmentFour newInstance(String s){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_ARGS,s);
        FragmentFour fragment = new FragmentFour();
        fragment.setArguments(bundle);
        return fragment;
    }

}