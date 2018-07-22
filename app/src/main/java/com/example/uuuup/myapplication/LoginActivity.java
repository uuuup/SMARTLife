package com.example.uuuup.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;//本地存储数据
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditText) findViewById(R.id.accountEt);
        passwordEdit = (EditText) findViewById(R.id.passwordEt);
        rememberPass = (CheckBox) findViewById(R.id.login_remember_pass);
        login = (Button) findViewById(R.id.loginBtn);

        boolean isRemember = pref.getBoolean("remember_pass", false);
        if(isRemember){
            //将账号和密码都设置到文本框中
            String account = pref.getString("account", "");//第二个参数代表默认值
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

        login.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                //如果账号是admin且密码是123456，就被认为登录成功
                if (checkPassword(account,password)){
                    editor = pref.edit();
                    if (rememberPass.isChecked()){//检查复选框是否被选中
                        editor.putBoolean("remember_pass", true);//如果复选框被选中则进行提交
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else{
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPassword(String account, String password){
        return account.equals("admin") && password.equals("123456");
    }
}
