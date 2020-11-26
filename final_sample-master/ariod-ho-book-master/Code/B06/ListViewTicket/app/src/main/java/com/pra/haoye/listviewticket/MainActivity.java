package com.pra.haoye.listviewticket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected TextView showtvobj, tmpobj;
    protected Button closebtnobj;
    protected ListView mlobj;

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
        showtvobj = findViewById(R.id.showtv);
        closebtnobj = findViewById(R.id.clsbtn);
        mlobj = findViewById(R.id.lvw);

        closebtnobj.setOnClickListener(returnBtn);


        mlobj.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tmpobj = (TextView) view;

                        String msg;
                        msg = "已購 : ";
                        msg += " " + tmpobj.getText();
                        showtvobj.setText(msg);
                    }
                }
        );
    }
}
