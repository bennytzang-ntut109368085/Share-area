package com.csl.haoye.addminuscount;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    protected TextView showtxtv;
    protected Button ad1, ad2, mi1, mi2, zero, help, close;
    public int counternmval, txtsize;

    private View.OnClickListener returnBtn = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    class CountOnClickLongListener implements android.view.View.OnLongClickListener{
        public boolean onLongClick(View v){
            showtxtv.setText("0");
            return true;
        }
    }


    android.view.View.OnLongClickListener CountListener = new CountOnClickLongListener();

    protected void findViews() {

        /* findViewById */

        showtxtv = findViewById(R.id.showtxtvw);
        ad1 = findViewById(R.id.add1);
        ad2 = findViewById(R.id.add2);
        mi1 = findViewById(R.id.miu1);
        mi2 = findViewById(R.id.miu2);
        zero = findViewById(R.id.zerobtn);
        help = findViewById(R.id.helpbtn);
        close = findViewById(R.id.closebtn);

        showtxtv.setOnLongClickListener(CountListener);

        txtsize = (int)showtxtv.getTextSize();

        ad1.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        showtxtv.setText(String.valueOf(++counternmval));
                    }
                }
        );

        ad2.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        showtxtv.setText(String.valueOf(counternmval += 2));
                    }
                }
        );

        mi1.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        showtxtv.setText(String.valueOf(--counternmval));
                    }
                }
        );

        mi2.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        showtxtv.setText(String.valueOf(counternmval -= 2));
                    }
                }
        );

        zero.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        showtxtv.setText(String.valueOf(counternmval = 0));
                    }
                }
        );

        help.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        String msg = "常按畫面即可歸 0 .";
                        Toast.makeText(
                                MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        close.setOnClickListener(returnBtn);

    }
}
