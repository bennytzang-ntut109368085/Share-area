package com.pra.haoye.tempconversion;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends Activity {
    protected RadioButton rbc, rbf, rbk;
    protected RadioGroup rbgcfk;
    protected Button closebtn;
    protected TextView shtvc, shtvf, shtvk;
    protected EditText ipev;
    public String temc, temf;
    public Double temval, tvcf,tvck;

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
        rbc = findViewById(R.id.rbc);
        rbf = findViewById(R.id.rbf);
        rbk = findViewById(R.id.rbk);
        rbgcfk = findViewById(R.id.rall);
        shtvc = findViewById(R.id.showtvc);
        shtvf = findViewById(R.id.showtvf);
        shtvk = findViewById(R.id.showtvk);
        ipev = findViewById(R.id.editText);

        closebtn = findViewById(R.id.closebtn);

        closebtn.setOnClickListener(returnBtn);

        temc = shtvc.getText().toString();
        temf = shtvf.getText().toString();

        ipev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                     int id = rbgcfk.getCheckedRadioButtonId();
                onClick(id);
            }
        });

        rbgcfk.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        onClick(checkedId);

                    }
                }
        );

    }

    public void onClick(int id){
        String numStr = ipev.getText().toString();
        if(!numStr.isEmpty()) {
            temval = Double.parseDouble(numStr);

            switch (id){
                case R.id.rbc:
                    shtvc.setText(temval + temc);
                    shtvf.setText((((9.0D/5.0D) * temval) + 32) + temf);
                    shtvk.setText((temval + 273.15) + "K");
                    break;
                case R.id.rbf:
                    shtvc.setText(((temval - 32)*(5.0D/9.0D)) + temc);
                    shtvf.setText(temval + temf);
                    shtvk.setText((((temval - 32)*(5.0D/9.0D)) + 273.15) + "K");
                    break;
                case R.id.rbk:
                    shtvc.setText((temval - 273.5) + temc);
                    shtvf.setText(((temval - 273.5) * (9.0D/5.0D) + 32) + temf);
                    shtvk.setText(temval + "K");
                    break;
            }
        }
    }
}
