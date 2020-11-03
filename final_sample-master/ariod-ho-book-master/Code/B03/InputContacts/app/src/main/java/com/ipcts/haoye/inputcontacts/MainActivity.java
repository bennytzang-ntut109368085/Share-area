package com.ipcts.haoye.inputcontacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected TextView showt, shows;
    protected Button sbtn;
    protected EditText intxt,fntxt, catxt, patxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    protected void findViews() {

        shows = findViewById(R.id.shows);
        showt = findViewById(R.id.showt);
        sbtn = findViewById(R.id.submitbtn);
        patxt = findViewById(R.id.passwdetxt);
        catxt = findViewById(R.id.calletxt);
        fntxt = findViewById(R.id.fnetxt);
        intxt = findViewById(R.id.lnetxt);

        sbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        showt.setText("預覽");
                        shows.setText("姓名 : " + fntxt.getText().toString() + ", " +
                        intxt.getText().toString() + "\n 電話 : " + catxt.getText().toString() +
                        "\n 密碼 : " + patxt.getText().toString());

                    }
                }
        );
    }
}
