package com.pra.haoye.drawcontact;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    protected TextView showtvobj;
    protected Button clebtnobj, subbtnobj, clsbtnobj;
    protected EditText nameetobj, phoneetobj;
    public int numdata = 0;
    public String temstr = "";
    protected int nametem, photem;

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

        clsbtnobj = findViewById(R.id.closebtn);
        clsbtnobj.setOnClickListener(returnBtn);

        clebtnobj = findViewById(R.id.cleatn);
        subbtnobj = findViewById(R.id.subtn);

        showtvobj = findViewById(R.id.showtv);
        nameetobj = findViewById(R.id.NaTxt);
        phoneetobj = findViewById(R.id.phTxt);

        clebtnobj.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        nameetobj.setText("");
                        phoneetobj.setText("");
                    }
                }
        );
        subbtnobj.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        nametem = nameetobj.length();
                        photem = phoneetobj.length();
                        if(nametem == 0 && photem == 0){
                            Toast.makeText(
                                    MainActivity.this,
                                    "姓名與電話不得為空",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }else if(nametem == 0){
                            Toast.makeText(
                                    MainActivity.this,
                                    "姓名不得為空",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }else if(photem == 0){
                            Toast.makeText(
                                    MainActivity.this,
                                    "電話不得為空",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }else{
                            numdata = ++numdata;
                            showtvobj.setVisibility(View.VISIBLE);
                            temstr = temstr + "第 " + numdata +" 筆"+
                                    "\n 姓名 : " + nameetobj.getText().toString() +
                                    "\n 電話 : " + phoneetobj.getText().toString() + "\n\n";
                            showtvobj.setText(temstr );
                        }
                    }
                }
        );
    }
}
