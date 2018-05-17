package com.example.uuuup.myapplication.base;

/**
 * Created by uuuup on 2018/5/17.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uuuup.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mContactNames; // 字符串数组

    public interface OnItemClickListener {
        void onItemClick(View v,int position);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View v,int position);
    }
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener= listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        onItemLongClickListener=listener;
    }

    public ContactAdapter(Context context, ArrayList contactNames) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mContactNames = contactNames;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ContactHolder Myholder = (ContactHolder) holder;
        Myholder.mTextView.setText(mContactNames.get(position));
        if (onItemClickListener!=null) {
            Myholder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(Myholder.itemView, Myholder.getLayoutPosition());
                }
            });
        }
        if (onItemLongClickListener!=null){
            Myholder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClick(Myholder.itemView,Myholder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    public void remove(int position) {
        mContactNames.remove(position);
        notifyItemRemoved(position);
    }

    public void add(String text, int position) {
        mContactNames.add(position, text);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mContactNames == null ? 0 : mContactNames.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder  {
        TextView mTextView;

        ContactHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.contact_name);
        }
    }
}