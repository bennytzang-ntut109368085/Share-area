package com.pra.haoye.intenttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.*;

public class Main2Activity extends Activity {
    protected Button clsbtnobj, topage1Finbtnobj, topage1Sysbtnobj, topage1Desbtnobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViews();
    }
    private View.OnClickListener returnBtn = new View.OnClickListener(){
        public void onClick(View v){
            Main2Activity.this.finish();
        }
    };
    protected void findViews() {
        clsbtnobj = findViewById(R.id.clsbtn);

        topage1Finbtnobj = findViewById(R.id.topage1btnFinish);
        topage1Sysbtnobj = findViewById(R.id.topage1btnSystemExit);
        topage1Desbtnobj = findViewById(R.id.topage1btnOnDestory);

        clsbtnobj.setOnClickListener(returnBtn);

        topage1Finbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                            /* haoye add intent func */
                        Intent IntentObj = new Intent();
                        IntentObj.setClass(Main2Activity.this, MainActivity.class);
                        Main2Activity.this.startActivity(IntentObj);
                        /* finish() */
                        /* Call this when your activity is done and should be closed. */
                        Main2Activity.this.finish();
                    }
                }
        );
        topage1Sysbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                            /* haoye add intent func */
                        Intent IntentObj = new Intent();
                        IntentObj.setClass(Main2Activity.this, MainActivity.class);
                        Main2Activity.this.startActivity(IntentObj);
                        /* System.exit(0) */
                        /* Causes the VM to stop running and the program to exit with the given exit status. */
                        System.exit(0);
                    }
                }
        );
        topage1Desbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                            /* haoye add intent func */
                        Intent IntentObj = new Intent();
                        IntentObj.setClass(Main2Activity.this, MainActivity.class);
                        Main2Activity.this.startActivity(IntentObj);
                        /* onDestory */
                        /* The system is temporarily destroying this instance of the activity to save space. */
                        Main2Activity.this.onDestory();
                    }
                }
        );
    }
    private void onDestory() {
        super.onDestroy();
    }
}
