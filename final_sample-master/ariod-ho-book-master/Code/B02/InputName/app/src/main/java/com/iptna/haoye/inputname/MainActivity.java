package com.iptna.haoye.inputname;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    protected EditText iptxt;
    protected Button sbtn;
    protected TextView txtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    protected void findViews() {
        sbtn = findViewById(R.id.sbtn);
        iptxt = findViewById(R.id.ipttxt);
        txtv = findViewById(R.id.txtv);

        sbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        String str = iptxt.getText().toString();
                        String msg = "Please input your name.";
                        if(str.length() == 0){
                            txtv.setText("Hello World!");
                            Toast.makeText(
                                    MainActivity.this,
                                    msg,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }else{
                            txtv.setText("Hi!, " + str + ".");
                        }
                    }
                }
        );
    }
}
