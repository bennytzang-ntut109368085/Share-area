package com.pra.haoye.energycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    protected double [] energyRate = { 3.1, 4.4, 13.2, 9.7, 5.1, 3.7};
    protected EditText weight, time;
    protected TextView total, mrate;
    protected Spinner spts;
    protected Button clsbtn, subbtn;

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

        total = findViewById(R.id.showtv);

        clsbtn = findViewById(R.id.clsbtn);
        clsbtn.setOnClickListener(returnBtn);

        weight = findViewById(R.id.weiet);
        time = findViewById(R.id.timeet);
        spts = findViewById(R.id.sper);
        mrate = findViewById(R.id.shpowtv);

        subbtn = findViewById(R.id.subbtn);

        subbtn.setOnClickListener(onCalc);
        spts.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mrate.setText(String.valueOf(energyRate[position]));
                        onFunc(view);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    public  View.OnClickListener onCalc = new View.OnClickListener(){
        public void onClick(View v){
            onFunc(v);
        }
    };

    public void onFunc (View v){
        String w = weight.getText().toString();
        String t = time.getText().toString();
        int wi = w.length();
        int ti = t.length();

        int pos = spts.getSelectedItemPosition();

        if(wi == 0 && ti == 0 ){
            total.setText("請輸入體重和運動時間");
        } else if(wi == 0){
            Toast.makeText(
                    MainActivity.this,
                    "請輸入體重",
                    Toast.LENGTH_SHORT)
                    .show();
        }else if(ti == 0){
            Toast.makeText(
                    MainActivity.this,
                    "請輸入時間",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            long kcal = Math.round(energyRate[pos] *
                    Double.parseDouble(w) *
                    Double.parseDouble(t));
            total.setText(String.format("消耗能量 %d 仟卡", kcal));
        }
    }
}
