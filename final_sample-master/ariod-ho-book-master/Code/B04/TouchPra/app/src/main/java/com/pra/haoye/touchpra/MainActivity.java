package com.pra.haoye.touchpra;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener{
    protected Button help, ble5, clo;
    protected TextView toutv;
    public int temval;

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

        toutv = findViewById(R.id.toutv);
        help = findViewById(R.id.helpbtn);
        ble5 = findViewById(R.id.bless5btn);
        clo = findViewById(R.id.closebtn);

        clo.setOnClickListener(returnBtn);

        help.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        String msg = "按下 bless 5 可以震動 5 秒，而長按 Touch 畫面則可以連續間隔的震動.";
                        Toast.makeText(
                                MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        toutv.setOnTouchListener(this);
        ble5.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if(v.getId() == R.id.bless5btn ){
                vb.vibrate(5000);
            }

            if(event.getAction() == MotionEvent.ACTION_DOWN){

                vb.vibrate(new long[]{0,100,1000,100},2);

            } else if(event.getAction() == MotionEvent.ACTION_UP){

                vb.cancel();

            }
        return true;
    }

}
