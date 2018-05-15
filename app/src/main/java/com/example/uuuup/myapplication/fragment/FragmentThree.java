package com.example.uuuup.myapplication.fragment;

import android.os.Bundle;

import com.example.uuuup.myapplication.Constants;

public class FragmentThree extends FragmentBase{

    public static FragmentThree newInstance(String s){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_ARGS,s);
        FragmentThree fragment = new FragmentThree();
        fragment.setArguments(bundle);
        return fragment;
    }
}
