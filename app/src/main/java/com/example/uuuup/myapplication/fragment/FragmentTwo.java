package com.example.uuuup.myapplication.fragment;

import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.base.ContactAdapter;
import com.example.uuuup.myapplication.base.DividerItemDecoration;

import com.example.uuuup.myapplication.chat.view.ChatRoomActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;


public class FragmentTwo extends Fragment {

    private RecyclerView contactList;
    private ArrayList<String> car_list = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private ContactAdapter adapter;
    private SearchView searchView;
    private boolean flag = false;
    private String[] contactNames;

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

        searchView = (SearchView) getView().findViewById(R.id.searchview);

        // 4. 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
            }
        });

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {//点击监听
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                startActivity(intent);
                //Toast.makeText(getActivity(),"the number"+position+"has been clicked",Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new ContactAdapter.OnItemLongClickListener() {//长点击监听
            @Override
            public void onItemLongClick(View v, int position) {
                //Toast.makeText(getActivity(),"the car "+car_list.get(position)+" has been removed",Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnOpenClickListener(new ContactAdapter.OnOpenClickListener() {
            @Override
            public void onOpenClick(View v, int position) {
                String str = car_list.get(position);
                car_list.remove(position);
                car_list.add(0, str);
                adapter.notifyItemRangeChanged(0,car_list.size());
            }
        });

        adapter.setOnDeleteClickListener(new ContactAdapter.OnDeleteClickListener(){
            @Override
            public void onDeleteClick(View v, int position) {
                car_list.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }
}