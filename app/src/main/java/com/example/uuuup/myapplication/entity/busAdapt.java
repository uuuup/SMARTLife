package com.example.uuuup.myapplication.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.utils.DrawableSwitch;

import java.util.List;

/**
 * Created by uuuup on 2018/5/19.
 */
public class busAdapt extends RecyclerView.Adapter<com.example.uuuup.myapplication.entity.busAdapt.ViewHolder> {

    private List<item_bus> mList;
    private SharedPreferences pref;//本地存储数据
    private SharedPreferences.Editor editor;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        DrawableSwitch checkBox;
        TextView bus_name;

        public ViewHolder(View view) {
            super(view);
            checkBox = (DrawableSwitch) view.findViewById(R.id.item_bus_drawableSwitch);
            bus_name = (TextView) view.findViewById(R.id.bus_name);
        }
    }

    public busAdapt(List<item_bus> msgList, Context context1) {
        mList = msgList;
        context = context1;
        pref = PreferenceManager.getDefaultSharedPreferences(context1);
    }

    @Override
    public com.example.uuuup.myapplication.entity.busAdapt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item, parent, false);
        return new com.example.uuuup.myapplication.entity.busAdapt.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final com.example.uuuup.myapplication.entity.busAdapt.ViewHolder holder, final int position) {
        final item_bus msg = mList.get(position);
        holder.bus_name.setText(msg.getBus_name());
        holder.checkBox.setSwitchOn(msg.getCheck());
        holder.checkBox.setListener(new DrawableSwitch.MySwitchStateChangeListener()
                {
                    @Override
                    public void mySwitchStateChanged(boolean isSwitchOn)
                    {
                        editor = pref.edit();
                        if(isSwitchOn){
                            editor.putBoolean(msg.getBus_name(), true);//如果复选框被选中则进行提交
                            Toast.makeText(context, msg.getBus_name(), Toast.LENGTH_LONG).show();
                        } else {
                            editor.putBoolean(msg.getBus_name(), false);
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