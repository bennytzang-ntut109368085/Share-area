package com.pra.haoye.foodmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    protected Button closebtn, cleanbtn, subbtn;
    protected CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, chk, chts;
    protected TextView showtv;

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

        int [] chkid = {R.id.cb1, R.id.cb2, R.id.cb3, R.id.cb4,
                R.id.cb5, R.id.cb6, R.id.cb7, R.id.cb8, R.id.textsize};
        for(int id:chkid){
            chk = findViewById(id);
            chk.setOnCheckedChangeListener(this);
        }

        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
        cb7 = findViewById(R.id.cb7);
        cb8 = findViewById(R.id.cb8);
        chts = findViewById(R.id.textsize);

        showtv = findViewById(R.id.showtv);


        cleanbtn = findViewById(R.id.cleatn);
        subbtn = findViewById(R.id.subtn);

        cleanbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        cb3.setChecked(false);
                        cb4.setChecked(false);
                        cb5.setChecked(false);
                        cb6.setChecked(false);
                        cb7.setChecked(false);
                        cb8.setChecked(false);
                    }
                }
        );

        subbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {

                        showtv.setVisibility(View.VISIBLE);
                        String msg = "";
                        for(CompoundButton chk:selected){
                            msg += "\n" + chk.getText();
                        }

                        if(msg.length() > 0){
                            msg = "所點餐點為 : " + msg;
                        }

                        showtv.setText(msg);
                    }
                }
        );

        closebtn = findViewById(R.id.closebtn);
        closebtn.setOnClickListener(returnBtn);

    }

    ArrayList<CompoundButton> selected = new ArrayList<>();

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView.getId() == R.id.textsize){
            if(isChecked){
                showtv.setTextSize(15);
            }else{
                showtv.setTextSize(20);
            }
            return;
        } else {
            if (isChecked){
                selected.add(buttonView);
            }else{
                selected.remove(buttonView);
            }
        }

    }

}
