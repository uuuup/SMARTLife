package com.example.uuuup.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uuuup.myapplication.utils.ChatBean;
import com.example.uuuup.myapplication.utils.ChatView;
import com.example.uuuup.myapplication.utils.SocketThread;
import com.example.uuuup.myapplication.utils.Msg;
import com.example.uuuup.myapplication.utils.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity  implements ChatView{

    private List<Msg> msgList = new ArrayList<Msg>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    private SocketThread thread;

    private String userId = String.valueOf(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //启动线程，接收服务器发送过来的数据
        thread = new SocketThread(this);
        thread.start();

        initMsgs(); // 初始化消息数据
        inputText = (EditText) findViewById(R.id.input_text);

        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);

        msgRecyclerView.setAdapter(adapter);

        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                    thread.sendMsg(content);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.sendMsg("exit");
        thread.interrupt();
    }



    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
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
        msgList.add(new Msg(bean.content, Msg.TYPE_RECEIVED));
        adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
        msgRecyclerView.scrollToPosition(msgList.size() - 1);
    }
}
