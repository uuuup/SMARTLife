package com.example.uuuup.myapplication.entity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.uuuup.myapplication.R;

import java.util.List;

/**
 * Created by uuuup on 2018/5/19.
 */
public class busAdapt extends RecyclerView.Adapter<com.example.uuuup.myapplication.entity.busAdapt.ViewHolder> {

    private List<item_bus> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView bus_name;

        public ViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox_bus);
            bus_name = (TextView) view.findViewById(R.id.bus_name);
        }
    }

    public busAdapt(List<item_bus> msgList) {
        mList = msgList;
    }

    @Override
    public com.example.uuuup.myapplication.entity.busAdapt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item, parent, false);
        return new com.example.uuuup.myapplication.entity.busAdapt.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.uuuup.myapplication.entity.busAdapt.ViewHolder holder, final int position) {
        final item_bus msg = mList.get(position);
        holder.bus_name.setText(msg.getBus_name());
        holder.checkBox.setChecked(msg.getCheck());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mList.get(position).setCheck(true);
                }
                else {
                    mList.get(position).setCheck(false);
                }
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return mList.size();
    }

}