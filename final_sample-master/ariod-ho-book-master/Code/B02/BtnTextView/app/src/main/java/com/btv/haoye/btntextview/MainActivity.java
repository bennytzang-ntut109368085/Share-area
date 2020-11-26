package com.btv.haoye.btntextview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private int txtsize, savesize;
    private TextView txtv;
    private Button bbtn, sbtn, rbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    protected void findViews() {
        sbtn = (Button)findViewById(R.id.sbtn);
        bbtn = (Button)findViewById(R.id.bbtn);
        rbtn = (Button)findViewById(R.id.rbtn);
        txtv = findViewById(R.id.txtv);

        savesize = (int)txtv.getTextSize();
        txtsize = savesize;

        bbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        String maxmsg = "Maximum Limit";
                            if(txtsize >= 80){
                                Toast.makeText(
                                        MainActivity.this,
                                        maxmsg,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                txtv.setTextSize(++txtsize);
                            }
                    }
                }
        );

        sbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        String minmsg = "Minimum Limit";
                        if(txtsize <= 8){
                            Toast.makeText(
                                    MainActivity.this,
                                    minmsg,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            txtv.setTextSize(--txtsize);
                        }
                    }
                }
        );

        rbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                            txtv.setTextSize(savesize);
                    }
                }
        );

    }

}
