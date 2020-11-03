package com.pra.haoye.seekbarpra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected Button clsbtn;
    protected TextView showtvobj;
    protected SeekBar skbrobj;

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

        clsbtn = findViewById(R.id.closebtn);
        clsbtn.setOnClickListener(returnBtn);

        showtvobj = findViewById(R.id.showtv);
        skbrobj = findViewById(R.id.seekBar);

        skbrobj.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        showtvobj.setText(progress + "");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

    }

}
