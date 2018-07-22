package com.example.uuuup.myapplication.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.uuuup.myapplication.R;

public class DefaultSettingActivity extends AppCompatActivity {

    private DrawableSwitch drawableSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_setting);
        drawableSwitch = (DrawableSwitch) findViewById(R.id.drawableSwitch);
        drawableSwitch
                .setListener(new DrawableSwitch.MySwitchStateChangeListener()
                {
                    @Override
                    public void mySwitchStateChanged(boolean isSwitchOn)
                    {
                        if(isSwitchOn){
                            Toast.makeText(getApplicationContext(), "开启", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
