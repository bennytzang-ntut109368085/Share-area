package com.example.user.khaoyesignin2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private EditText IdTxt;
    private EditText NaTxt;
    private EditText phTxt;
    private Button simbtn;
    private Button clebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    protected void findViews() {
        IdTxt = (EditText)findViewById(R.id.IdTxt);
        NaTxt = (EditText)findViewById(R.id.NaTxt);
        phTxt = (EditText)findViewById(R.id.phTxt);
        simbtn = (Button)findViewById(R.id.simbtn);
        clebtn = (Button)findViewById(R.id.clebtn);

        simbtn.setOnClickListener(this);
        clebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clebtn:
                IdTxt.setText("");
                NaTxt.setText("");
                phTxt.setText("");
             break;
            case R.id.simbtn:
                String id = IdTxt.getText().toString();
                String name = NaTxt.getText().toString();
                String mobile = phTxt.getText().toString();
                String msg =
                        getString(R.string.yourInfo) + id + "\n" + name
                                + "\n" + mobile;
                Toast.makeText(
                        MainActivity.this,
                        msg,
                        Toast.LENGTH_LONG)
                        .show();

                /* haoye add intent func output data to maop */
                Intent IntentObj = new Intent();
                IntentObj.setClass(this, MAOP.class);
                this.startActivity(IntentObj);

                Bundle bundle = new Bundle();

                bundle.putString("IdOp",id);
                bundle.putString("NameOp",name);
                bundle.putString("PhOp",mobile);
                IntentObj.putExtras(bundle);
                startActivity(IntentObj);

             break;
        }

    }

}
