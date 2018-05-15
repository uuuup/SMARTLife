package com.example.uuuup.myapplication.fragment;

import com.example.uuuup.myapplication.ChatActivity;
import com.example.uuuup.myapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentTwo extends Fragment implements View.OnClickListener {

    private Button button;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two,container,false);
        button = (Button) view.findViewById(R.id.chat);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }

    public static FragmentTwo newInstance(String s){
        FragmentTwo fragment = new FragmentTwo();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.chat:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
