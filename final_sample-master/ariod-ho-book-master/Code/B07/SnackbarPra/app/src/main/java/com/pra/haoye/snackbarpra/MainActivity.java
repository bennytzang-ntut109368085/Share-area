package com.pra.haoye.snackbarpra;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
    protected ListView lv;
    protected Button clsbtnobj;
    protected Snackbar sbar;
    public String[] queArr = { "100 * 10", "5 * 5", "8 * 8"};
    public String[] ansArr = { "1000", "25", "64"};


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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                queArr
        );

        sbar = Snackbar.make(findViewById(R.id.root), "", Snackbar.LENGTH_SHORT);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(returnBtn);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sbar.setText(ansArr[position]);
                sbar.show();
            }
        });

    }

}
