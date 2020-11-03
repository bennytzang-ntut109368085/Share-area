package com.pra.haoye.fragmenttabspra;

import android.app.Activity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();
    }
    protected void Func(){
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //1
        tabHost.addTab(tabHost.newTabSpec("點名")
                        .setIndicator("點名"),
                Tab1Fragment.class,
                null);
        //2
        tabHost.addTab(tabHost.newTabSpec("請假")
                        .setIndicator("請假"),
                Tab2Fragment.class,
                null);
        //3
        tabHost.addTab(tabHost.newTabSpec("查詢")
                        .setIndicator("查詢"),
                Tab3Fragment.class,
                null);
        //4
        tabHost.addTab(tabHost.newTabSpec("座位表")
                        .setIndicator("座位表"),
                Tab4Fragment.class,
                null);
    }
}
