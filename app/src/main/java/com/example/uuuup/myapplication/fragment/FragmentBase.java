package com.example.uuuup.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uuuup.myapplication.Constants;
import com.example.uuuup.myapplication.R;

/**
 * Created by uuuup on 2018/5/14.
 */

public class FragmentBase extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        TextView mFragmentText = (TextView) view.findViewById(R.id.tv_fragment_text);
        Bundle bundle = getArguments();
        String args = bundle.getString(Constants.KEY_ARGS);
        mFragmentText.setText(args);
        return view;
    }
}
