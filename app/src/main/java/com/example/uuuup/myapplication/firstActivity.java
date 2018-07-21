package com.example.uuuup.myapplication;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.uuuup.myapplication.base.*;
import com.example.uuuup.myapplication.fragment.FragmentOne;
import com.example.uuuup.myapplication.fragment.FragmentThree;
import com.example.uuuup.myapplication.fragment.FragmentTwo;

public class firstActivity extends TitleActivity implements BottomNavigationBar.OnTabSelectedListener  {
    private BottomNavigationBar mBottomNavigationBar;
    private FragmentOne mFragmentOne;
    private FragmentTwo mFragmentTwo;
    private FragmentThree mFragmentThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }

        setTitle("智慧公交");
        //showBackwardView(,true);
        showForwardView(R.string.text_forward,false);


        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        /*** the setting for BadgeItem ***/

        BadgeItem badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("10")
                .setBackgroundColorResource(R.color.orange)
                .setBorderWidth(0);

        /*** the setting for BottomNavigationBar ***/

//        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        mBottomNavigationBar.setBarBackgroundColor(R.color.blue);//set background color for navigation bar
        mBottomNavigationBar.setInActiveColor(R.color.white);//unSelected icon color
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.bus, "公交").setActiveColorResource(R.color.green))
                .addItem(new BottomNavigationItem(R.drawable.room, "聊天室").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.user_center, "个人中心").setActiveColorResource(R.color.lime))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);
        //setDefaultFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mFragmentOne == null) {
            mFragmentOne = FragmentOne.newInstance();
        }
        transaction.replace(R.id.content, mFragmentOne);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
            case 0:
                mFragmentOne = FragmentOne.newInstance();
                transaction.replace(R.id.content, mFragmentOne);
                break;
            case 1:
                if (mFragmentTwo == null) {
                    mFragmentTwo = FragmentTwo.newInstance();
                }
                transaction.replace(R.id.content, mFragmentTwo);
                break;
            case 2:
                if (mFragmentThree == null) {
                    mFragmentThree = FragmentThree.newInstance();
                }
                transaction.replace(R.id.content, mFragmentThree);
                break;
            default:
                mFragmentOne = FragmentOne.newInstance();
                transaction.replace(R.id.content, mFragmentOne);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

}
