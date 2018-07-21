package com.example.uuuup.myapplication.chat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.chat.bean.ChatBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/16 0016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatBean> mList;
    private LayoutInflater mInflater;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String userId;
    private String OldUserId;

    public ChatAdapter(Context ctx, String userId, String OldUserId,  List<ChatBean> List) {
        mInflater = LayoutInflater.from(ctx);
        mList = List;
        this.userId = userId;
        this.OldUserId = OldUserId;
    }

    public void setUserId(String str1, String str2){
        userId = str1;
        OldUserId = str2;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.idLeft)
        TextView idLeft;
        @Bind(R.id.idRight)
        TextView idRight;
        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvLeft)
        TextView tvLeft;
        @Bind(R.id.tvRight)
        TextView tvRight;
        @Bind(R.id.tvTip)
        TextView tvTip;
        @Bind(R.id.frameLeft)
        FrameLayout frameLeft;
        @Bind(R.id.frameRight)
        FrameLayout frameRight;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView  = mInflater.inflate(R.layout.new_lv_item_chat, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatBean bean = mList.get(position);
        if (position == 0 || position > 0 && bean.time - mList.get(position - 1).time > 60000) {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(format.format(bean.time));
        } else {
            holder.tvTime.setVisibility(View.GONE);
        }

        holder.idLeft.setVisibility(View.GONE);
        holder.frameLeft.setVisibility(View.GONE);

        holder.idRight.setVisibility(View.GONE);
        holder.frameRight.setVisibility(View.GONE);

        if (bean.content.equals("exit")) {
            holder.tvTip.setVisibility(View.VISIBLE);
            holder.tvTip.setText(bean.name.equals(userId) ? "您已退出讨论组" : 's'+ bean.name + "已退出讨论组");
        } else if (bean.content.equals("join")) {
            holder.tvTip.setVisibility(View.VISIBLE);
            holder.tvTip.setText(bean.name.equals(userId) ? "您已加入讨论组" : 's'+ bean.name + "加入讨论组");
        } else {
            holder.tvTip.setVisibility(View.GONE);
            holder.idLeft.setVisibility(bean.name.equals(userId) ? View.GONE : View.VISIBLE);
            holder.frameLeft.setVisibility(bean.name.equals(userId) ? View.GONE : View.VISIBLE);

            holder.idRight.setVisibility(bean.name.equals(userId) ? View.VISIBLE : View.GONE);
            holder.frameRight.setVisibility(bean.name.equals(userId) ? View.VISIBLE : View.GONE);
            if (bean.name.equals(userId) || bean.name.equals(OldUserId)) {
                holder.idRight.setText('s' + bean.name);
                holder.tvRight.setText(bean.content);
            } else {
                holder.idLeft.setText('s' + bean.name);
                holder.tvLeft.setText(bean.content);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
