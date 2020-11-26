package com.pra.haoye.intenttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.*;

public class MainActivity extends Activity {
    protected Button clsbtnobj, topage2Finbtnobj, topage2Sysbtnobj, topage2Desbtnobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    private View.OnClickListener returnBtn = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.finish();
        }
    };
    protected void findViews() {
        clsbtnobj = findViewById(R.id.clsbtn);

        topage2Finbtnobj = findViewById(R.id.topage2btnFinish);
        topage2Sysbtnobj = findViewById(R.id.topage2btnSystemExit);
        topage2Desbtnobj = findViewById(R.id.topage2btnOnDestory);

        clsbtnobj.setOnClickListener(returnBtn);

        topage2Finbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                            /* haoye add intent func */
                        Intent IntentObj = new Intent();
                        IntentObj.setClass(MainActivity.this, Main2Activity.class);
                        MainActivity.this.startActivity(IntentObj);
                        /* finish() */
                        /* Call this when your activity is done and should be closed. */
                        MainActivity.this.finish();
                    }
                }
        );
        topage2Sysbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                            /* haoye add intent func */
                        Intent IntentObj = new Intent();
                        IntentObj.setClass(MainActivity.this, Main2Activity.class);
                        MainActivity.this.startActivity(IntentObj);
                        /* System.exit(0) */
                        /* Causes the VM to stop running and the program to exit with the given exit status. */
                        System.exit(0);
                    }
                }
        );
        topage2Desbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                            /* haoye add intent func */
                        Intent IntentObj = new Intent();
                        IntentObj.setClass(MainActivity.this, Main2Activity.class);
                        MainActivity.this.startActivity(IntentObj);
                        /* onDestory */
                        /* The system is temporarily destroying this instance of the activity to save space. */
                        MainActivity.this.onDestory();
                    }
                }
        );
    }
    private void onDestory() {
        super.onDestroy();
    }
}

