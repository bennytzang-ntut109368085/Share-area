package com.pra.haoye.memopro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends Activity {
    protected Button savebtnobj, cancelbtnobj;
    protected EditText ctetobj;
    protected TextView numtvobj;
    protected String temnumstrobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViews();
    }
    protected void findViews() {

        savebtnobj = findViewById(R.id.savebtn);
        cancelbtnobj = findViewById(R.id.cancelbtn);
        ctetobj = findViewById(R.id.ctet);

        Intent IntentObj = getIntent(); /* 取得傳入的 Intent 物件 */
        Bundle bundle = IntentObj.getExtras();
        temnumstrobj = bundle.getString("Num");
        int no = Integer.parseInt(temnumstrobj);
        String s = bundle.getString("Con");

        numtvobj = findViewById(R.id.numtv);
        numtvobj.setText(no + "."); /* 在畫面左上角顯示編號 */
        ctetobj = findViewById(R.id.ctet);
        if(s.length() > 3){
            ctetobj.setText(s.substring(3)); /* 將傳來的備忘資料去除前3個字, 然後填入編輯元件中 */
        }

        savebtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        Bundle bundle = new Bundle();
                        IntentObj.setClass(Main2Activity.this, MainActivity.class);
                        bundle.putString("ReCon", ctetobj.getText().toString());
                        bundle.putString("ReNum", temnumstrobj);
                        IntentObj.putExtras(bundle);
                        Main2Activity.this.startActivity(IntentObj);
                        Main2Activity.this.finish();

                    }
                }
        );

        cancelbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setClass(Main2Activity.this, MainActivity.class);
                        Main2Activity.this.startActivity(IntentObj);
                        Main2Activity.this.onDestory();
                    }
                }
        );
    }
    private void onDestory() {
        super.onDestroy();
        System.exit(0);
    }

}
