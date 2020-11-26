package com.pra.haoye.memopro2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    protected String[] MemoStr = { /* 預設的備忘內容 */
            "1. ", "2. ", "3. ","4. ","5. ","6. " };
    protected ListView lvobj;
    public int numobj;
    public String temnumobj;

    /* 顯示備忘錄的 ListView */
    protected ArrayAdapter<String> aaobj;
    /* ListView 與備忘資料的橋樑 */
    protected Button helpbtnobj, clsbtnobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    protected void findViews() {
        helpbtnobj = findViewById(R.id.helpbtn);
        clsbtnobj = findViewById(R.id.clsbtn);
        lvobj = findViewById(R.id.listView);
        aaobj = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, MemoStr);
        lvobj.setAdapter(aaobj);

        helpbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        String msg = "按下編輯\n長按清除";
                        Toast.makeText(
                                MainActivity.this,
                                msg,
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }
        );
        clsbtnobj.setOnClickListener(recls);
        lvobj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView a, View v, int pos, long id) {

                Intent IntentObj = new Intent(MainActivity.this, Main2Activity.class);
                IntentObj.putExtra("Num", pos + 1);      /* 附加編號 */
                IntentObj.putExtra("Con", MemoStr[pos]); /* 附加備忘項目的內容 */
                startActivityForResult(IntentObj, pos); /* 啟動 Main2Activity 並以項目位置為識別碼 */

            }
        });

        lvobj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> a, View v, int pos, long id) {
                MemoStr[pos] = (pos + 1) + "."; /* 將內容清除 (只剩編號) */
                aaobj.notifyDataSetChanged();  /* 通知 Adapter 要更新陣列內容 */
                return true;     			/* 傳回 true 表示此事件已處理 */
            }

        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent it) {
        if(resultCode == RESULT_OK) {
            MemoStr[requestCode] = it.getStringExtra("Con"); /* 使用傳回的資料更新陣列內容 */
            aaobj.notifyDataSetChanged(); /* 通知 Adapter 陣列內容有更新 */
            String msg = "備忘資料於\n" + it.getStringExtra("Date") + "\n 修改";
            Toast.makeText(
                    MainActivity.this,
                    msg,
                    Toast.LENGTH_LONG)
                    .show();
        }

    }
    private View.OnClickListener recls = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.onDestory();
        }
    };
    private void onDestory() {
        super.onDestroy();
        System.exit(0);
    }
}
