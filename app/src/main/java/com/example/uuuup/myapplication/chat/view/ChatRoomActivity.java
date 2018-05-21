package com.example.uuuup.myapplication.chat.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.chat.bean.ChatBean;
import com.example.uuuup.myapplication.chat.socket.SocketThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatRoomActivity extends AppCompatActivity implements ChatView {
    @Bind(R.id.listView)
    RecyclerView msgRecyclerView;
    @Bind(R.id.etContent)
    EditText etContent;
    private ChatAdapter mAdapter;
    private List<ChatBean> Bean_List = new ArrayList<ChatBean>();

    private String userId = String.valueOf(System.currentTimeMillis());

    private SocketThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ChatAdapter(this, userId, Bean_List);
        msgRecyclerView.setAdapter(mAdapter);

        //启动线程，接收服务器发送过来的数据
        thread = new SocketThread(this);
        thread.start();
    }

    /**
     * 如果连接出现异常，弹出AlertDialog！
     */
    public void ShowDialog(String msg) {
        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    @OnClick(R.id.btnSend)
    public void onClick(View view) {
        String msg = etContent.getText().toString();
        thread.sendMsg(msg);
        etContent.setText(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.sendMsg("exit");
        thread.interrupt();
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void showDiaolg(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        }).show();
    }

    @Override
    public void receiveMsg(ChatBean bean) {
        if (bean == null) return;

        Bean_List.add(bean);
        mAdapter.notifyItemInserted(Bean_List.size() - 1); // 当有新消息时，刷新ListView中的显示
        msgRecyclerView.scrollToPosition(Bean_List.size() - 1); // 将ListView定位到最后一行
    }
}
