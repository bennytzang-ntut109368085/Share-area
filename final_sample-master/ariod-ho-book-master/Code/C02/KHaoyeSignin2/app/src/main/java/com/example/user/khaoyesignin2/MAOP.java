package com.example.user.khaoyesignin2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MAOP extends Activity {
    private TextView SIdTxt;
    private TextView SNaTxt;
    private TextView SphTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maop);

        findViews();
    }
    protected void findViews() {
        SIdTxt = (TextView)findViewById(R.id.maopIdShow);
        SNaTxt = (TextView)findViewById(R.id.maopNameShow);
        SphTxt = (TextView)findViewById(R.id.maopPhoneShow);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String idSave = bundle.getString("IdOp");
        String NaSave = bundle.getString("NameOp");
        String PhSave = bundle.getString("PhOp");

        SIdTxt.setText(idSave);
        SNaTxt.setText(NaSave);
        SphTxt.setText(PhSave);

    }
}
