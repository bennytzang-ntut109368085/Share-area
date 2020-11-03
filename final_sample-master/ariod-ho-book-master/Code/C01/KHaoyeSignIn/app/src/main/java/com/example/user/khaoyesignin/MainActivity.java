package com.example.user.khaoyesignin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText IdTxt;
    private EditText NaTxt;
    private EditText phTxt;
    private Button simbtn;
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

        simbtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
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
                    }
                }
        );
    }
}
