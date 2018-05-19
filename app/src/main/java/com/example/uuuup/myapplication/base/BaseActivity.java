package com.example.uuuup.myapplication.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 */
public class BaseActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View initV(@IdRes int id) {
        return findViewById(id);
    }

    public View initVclick(@IdRes int id) {
        View view = initV(id);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
    }
}
