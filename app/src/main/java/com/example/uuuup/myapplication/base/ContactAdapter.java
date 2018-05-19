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
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.uuuup.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mContactNames; // 字符串数组

    public List<String> getmContactNames(){
        return mContactNames;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    public interface OnItemClickListener {
        void onItemClick(View v,int position);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View v,int position);
    }
    public interface OnOpenClickListener{
        void onOpenClick(View v, int position);
    }
    public interface OnDeleteClickListener{
        void onDeleteClick(View v, int position);
    }

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnOpenClickListener onOpenClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener= listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        onItemLongClickListener=listener;
    }

    public void setOnOpenClickListener(OnOpenClickListener listener){
        onOpenClickListener= listener;
    }
    public void setOnDeleteClickListener(OnDeleteClickListener listener){
        onDeleteClickListener= listener;
    }



    public ContactAdapter(Context context, ArrayList contactNames) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mContactNames = contactNames;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(mLayoutInflater.inflate(R.layout.item_swipe, parent, false));
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
        if (onOpenClickListener != null){
            Myholder.tvSwipeOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOpenClickListener.onOpenClick(Myholder.tvSwipeOpen,Myholder.getLayoutPosition());
                }
            });
        }
        if (onDeleteClickListener != null){
            Myholder.tvSwipeDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClick(Myholder.tvSwipeDelete,Myholder.getLayoutPosition());
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

    public void clear() {
        final int size = getItemCount();
        mContactNames.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<String>  list) {
        final int size = list.size();
        mContactNames.addAll(0,list);
        for (String str : mContactNames){
            System.out.print("wangpu"+str);
        }
        notifyItemRangeChanged(0, size);
    }

    @Override
    public int getItemCount() {
        return mContactNames == null ? 0 : mContactNames.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder  {
        TextView mTextView;
        TextView tvSwipeOpen;
        TextView tvSwipeDelete;

        ContactHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.contact_name);
            tvSwipeOpen = (TextView) view.findViewById(R.id.swipe_open);
            tvSwipeDelete= (TextView) view.findViewById(R.id.swipe_delete);
        }
    }
}