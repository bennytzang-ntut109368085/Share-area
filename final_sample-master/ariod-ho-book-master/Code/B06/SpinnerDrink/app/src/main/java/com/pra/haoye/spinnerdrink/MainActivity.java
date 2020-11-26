package com.pra.haoye.spinnerdrink;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected TextView showtvobj;
    protected Button closebtnobj, subbtnobj;
    protected Spinner classobj, levelobj;
    protected String[] tempSet1 = { "冰", "去冰", "溫"};
    protected String[] tempSet2 = { "冰", "去冰"};

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
        subbtnobj = findViewById(R.id.subbtn);
        closebtnobj = findViewById(R.id.clsbtn);

        closebtnobj.setOnClickListener(returnBtn);

        classobj = findViewById(R.id.sperclass);
        levelobj = findViewById(R.id.sperlevel);

        classobj.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] tempSet;
                        if(position == 3){
                            tempSet = tempSet2;
                        } else {
                            tempSet = tempSet1;

                        }
                        ArrayAdapter<String> tempAd = new ArrayAdapter<String>(MainActivity.this
                                , android.R.layout.simple_spinner_dropdown_item,tempSet
                        );
                        tempAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        levelobj.setAdapter(tempAd);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        subbtnobj.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        String msg = classobj.getSelectedItem() + ", " + /* 取得飲料名稱 */
                                levelobj.getSelectedItem(); /* 取得甜度選項 */

                        /* 將訂單內容顯示在文字方塊中 */
                        showtvobj.setText(msg);
                    }
                }
        );


    }

}
