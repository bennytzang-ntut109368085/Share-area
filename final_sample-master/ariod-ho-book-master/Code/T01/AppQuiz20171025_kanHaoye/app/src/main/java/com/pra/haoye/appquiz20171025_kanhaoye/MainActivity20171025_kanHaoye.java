package com.pra.haoye.appquiz20171025_kanhaoye;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity20171025_kanHaoye extends Activity {
    protected Button clobtn, subtn, cleatn;
    protected EditText txtid, nametxt, phonetxt;
    public String temstr = "";
    protected TextView showtvobj;
    public int numdata = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main20171025_kan_haoye);
        findViews();
    }

    private View.OnClickListener returnBtn = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity20171025_kanHaoye.this.finish();
        }
    };

    protected void findViews() {
        txtid =findViewById(R.id.IdTxt);
        nametxt = findViewById(R.id.NaTxt);
        phonetxt = findViewById(R.id.phTxt);
        showtvobj = findViewById(R.id.showtv);
        clobtn = findViewById(R.id.closebtn);
        subtn = findViewById(R.id.subtn);
        cleatn = findViewById(R.id.cleatn);

        clobtn.setOnClickListener(returnBtn);

        cleatn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        txtid.setText("");
                        nametxt.setText("");
                        phonetxt.setText("");
                    }
                }
        );

        subtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        numdata = ++numdata;
                        showtvobj.setVisibility(View.VISIBLE);
                        temstr = temstr + "第 " + numdata +" 筆"+
                                "\n 身分證字號 : " + txtid.getText().toString() +
                                "\n 姓名 : " + nametxt.getText().toString() +
                                "\n 電話 : " + phonetxt.getText().toString() + "\n\n";
                        showtvobj.setText(temstr );


                    }
                }
        );

    }
}
