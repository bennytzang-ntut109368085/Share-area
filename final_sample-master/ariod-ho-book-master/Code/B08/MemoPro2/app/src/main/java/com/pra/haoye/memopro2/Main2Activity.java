package com.pra.haoye.memopro2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class Main2Activity extends Activity {
    protected Button savebtnobj, cancelbtnobj;
    protected EditText ctetobj;
    protected TextView numtvobj, timeobj;
    protected String DateObj;

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
        timeobj = findViewById(R.id.timetv);
        Intent IntentObj = getIntent(); /* 取得傳入的 Intent 物件 */

        String s = IntentObj.getStringExtra("Con");  /* 讀出名為 "備忘" 的 String 資料 */
        numtvobj = findViewById(R.id.numtv);

        numtvobj.setText(s.substring(0, 2)); /* 在畫面左上角顯示編號 */
        DateObj = new Date().toString();
        timeobj.setText("Time : " + DateObj);

        if(s.length() > 3){
            ctetobj.setText(s.substring(3)); /* 將傳來的備忘資料去除前3個字, 然後填入編輯元件中 */
        }
        savebtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent reIntentObj = new Intent();
                        reIntentObj.putExtra("Con", numtvobj.getText() + " " + ctetobj.getText()); /* 附加項目編號與修改後的內容 */
                        reIntentObj.putExtra("Date", DateObj);
                        setResult(RESULT_OK, reIntentObj); /* 傳回代表成功的結果碼, 以及修改的資料 */
                        Main2Activity.this.finish();    /* 結束活動 */
                    }
                }
        );
        cancelbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        setResult(RESULT_CANCELED); /* 傳回代表取消的結果碼 */
                        Main2Activity.this.finish();    /* 結束活動 */
                    }
                }
        );
    }


}
