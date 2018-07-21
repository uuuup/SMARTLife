package com.example.uuuup.myapplication.chat.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.chat.bean.ChatBean;
import com.example.uuuup.myapplication.chat.socket.SocketThread;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private String olduserId;
    private String userId = String.valueOf(System.currentTimeMillis()) + threeRand();

    private SocketThread thread;
    private TextView mTitleTextView ;
    private LinearLayout mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) { actionBar.hide(); }

        setContentView(R.layout.activity_chat_room);

        mTitleTextView = (TextView) findViewById(R.id.title_chat);
        String bus_name = getIntent().getStringExtra("busname");
        mTitleTextView.setText(bus_name);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        olduserId = userId;
        mAdapter = new ChatAdapter(this, userId, olduserId, Bean_List);
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
        long start_time = Long.valueOf(userId.substring(0,userId.length()-3));
        long end_time = System.currentTimeMillis();
        if (end_time - start_time > 1000 * 10){
            olduserId = userId;
            userId = String.valueOf(end_time) + threeRand();
            mAdapter.setUserId(userId,olduserId);
        }
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

    @Override
    public String getOldUserId() {
        return  olduserId;
    }

    private String threeRand() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
}
