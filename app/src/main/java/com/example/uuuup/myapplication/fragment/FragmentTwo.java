package com.example.uuuup.myapplication.fragment;

import com.example.uuuup.myapplication.ChatActivity;
import com.example.uuuup.myapplication.LoginActivity;
import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.base.ContactAdapter;
import com.example.uuuup.myapplication.base.DividerItemDecoration;
import com.example.uuuup.myapplication.firstActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentTwo extends Fragment {

    private RecyclerView contactList;
    private String[] contactNames;
    private ArrayList<String> car_list = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private ContactAdapter adapter;
    private boolean flag = false;

    public static FragmentTwo newInstance(){
        FragmentTwo fragment = new FragmentTwo();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if (flag == false){
            contactNames = getDefaultcar();
            for (String str : contactNames){
                car_list.add(str);
            }
            flag = true;
        }
        initview();
    }

    private String[] getDefaultcar(){
        return new String[] { "1路", "2路", "3路", "4路", "5路"};
    }
    private void initview(){
        contactList = (RecyclerView) getView().findViewById(R.id.contact_list);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ContactAdapter(getContext(), car_list);
        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {//点击监听
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
                //Toast.makeText(getActivity(),"the number"+position+"has been clicked",Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new ContactAdapter.OnItemLongClickListener() {//长点击监听
            @Override public void onItemLongClick(View v, int position) {
                //Toast.makeText(getActivity(),"the car "+car_list.get(position)+" has been removed",Toast.LENGTH_SHORT).show();
            }
        });
        Button button_add = (Button) getView().findViewById(R.id.add_item);
        button_add.setOnClickListener(new View.OnClickListener() {//增加
            @Override
            public void onClick(View v) {
                car_list.add(0, "new car");
                adapter.notifyItemInserted(0);
                contactList.scrollToPosition(0);
            }
        });
        Button button_delete = (Button) getView().findViewById(R.id.delete_item);
        button_delete.setOnClickListener(new View.OnClickListener() {//删除
            @Override
            public void onClick(View v) {
                if (car_list.size() != 0){
                    car_list.remove(car_list.size()-1);
                    adapter.notifyItemRemoved(car_list.size());
                }

            }
        });
    }

}
