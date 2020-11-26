package com.csl.haoye.ticketmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected TextView showtvobj;
    protected Button subtn, close;
    protected RadioButton r1btn, r2btn, r11, r12, r13, r21, r22, r23, r24;
    protected RadioGroup tktypeobj, tknumberobj;


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

    protected void findViews() {
        showtvobj = findViewById(R.id.showtv);
        subtn = findViewById(R.id.subtn);
        close = findViewById(R.id.closebtn);
        tktypeobj = findViewById(R.id.rg1);
        tknumberobj = findViewById(R.id.rg2);

        r11 = findViewById(R.id.r11);
        r12 = findViewById(R.id.r12);
        r13 = findViewById(R.id.r13);
        r21 = findViewById(R.id.r21);
        r22 = findViewById(R.id.r22);
        r23 = findViewById(R.id.r23);
        r24 = findViewById(R.id.r24);

        r1btn = findViewById(tktypeobj.getCheckedRadioButtonId());
        r2btn = findViewById(tknumberobj.getCheckedRadioButtonId());

        close.setOnClickListener(returnBtn);

        subtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        showtvobj.setVisibility(View.VISIBLE);
                        switch (tktypeobj.getCheckedRadioButtonId()){
                            case R.id.r11:
                                if( tknumberobj.getCheckedRadioButtonId() == R.id.r21){
                                    showtvobj.setText("買 " + r11.getText() + "\n 總計 " + r21.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r22){
                                    showtvobj.setText("買 " + r11.getText() + "\n 總計 " + r22.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r23){
                                    showtvobj.setText("買 " + r11.getText() + "\n 總計 " + r23.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r24){
                                    showtvobj.setText("買 " + r11.getText() + "\n 總計 " + r24.getText() );
                                }
                                break;
                            case R.id.r12:
                                if( tknumberobj.getCheckedRadioButtonId() == R.id.r21){
                                    showtvobj.setText("買 " + r12.getText() + "\n 總計 " + r21.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r22){
                                    showtvobj.setText("買 " + r12.getText() + "\n 總計 " + r22.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r23){
                                    showtvobj.setText("買 " + r12.getText() + "\n 總計 " + r23.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r24){
                                    showtvobj.setText("買 " + r12.getText() + "\n 總計 " + r24.getText() );
                                }
                                break;
                            case R.id.r13:
                                if( tknumberobj.getCheckedRadioButtonId() == R.id.r21){
                                    showtvobj.setText("買 " + r13.getText() + "\n 總計 " + r21.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r22){
                                    showtvobj.setText("買 " + r13.getText() + "\n 總計 " + r22.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r23){
                                    showtvobj.setText("買 " + r13.getText() + "\n 總計 " + r23.getText() );
                                }else if(tknumberobj.getCheckedRadioButtonId() == R.id.r24){
                                    showtvobj.setText("買 " + r13.getText() + "\n 總計 " + r24.getText() );
                                }
                                break;
                        }

                    }
                }
        );
    }
}
