package com.txtcol.haoye.textcolor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    protected Button runbtn;
    protected TextView rtxtv, gtxtv, btxtv;
    protected LinearLayout colb;
    private int red, green, blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    protected void findViews() {
        runbtn = findViewById(R.id.runbtn);
        rtxtv = findViewById(R.id.rtxt);
        gtxtv = findViewById(R.id.gtxt);
        btxtv = findViewById(R.id.btxt);
        colb = findViewById(R.id.colbreak);



        runbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        Random ranxval = new Random();

        /* R */
                        red = ranxval.nextInt(256);
                        rtxtv.setText("R " + red);
                        rtxtv.setTextColor(Color.rgb( red, 0, 0));

        /* G */
                        green = ranxval.nextInt(256);
                        gtxtv.setText("G " + green);
                        gtxtv.setTextColor(Color.rgb( 0, green, 0));

        /* B */
                        blue = ranxval.nextInt(256);
                        btxtv.setText("B " + blue);
                        btxtv.setTextColor(Color.rgb( 0, 0, blue));

                        runbtn.setTextColor(Color.rgb( red, green, blue));

                        colb.setBackgroundColor(Color.rgb( red, green, blue));
                    }
                }
        );

    }
}
