package com.pra.haoye.listviewmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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

    ArrayList<String> selected = new ArrayList<>();

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

                        String item = tmpobj.getText().toString();

                        if(selected.contains(item)){
                            selected.remove(item);
                        } else {
                            selected.add(item);
                        }

                        String msg;

                        if(selected.size() > 0){
                            msg = "已點 : ";
                            for(String str:selected){
                                msg+= " " + str;
                            }
                            showtvobj.setText(msg);
                        } else if (selected.size() == 0){
                            msg = "尚未點餐";
                            showtvobj.setText(msg);
                        }
                    }
                }
        );
    }
}
