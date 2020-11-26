package com.pra.haoye.memopro;

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

        Intent reIntentObj = getIntent(); /* 取得傳入的 Intent 物件 */
        Bundle bundle = reIntentObj.getExtras();

        if (bundle != null && bundle.containsKey("ReCon") && bundle.containsKey("ReNum"))
        {
            String temreconobj = bundle.getString("ReCon");
            String temrenumstobj = bundle.getString("ReNum");
            int temrenuminobj = Integer.parseInt(temrenumstobj);
            MemoStr[temrenuminobj - 1] = temrenumstobj + ". " + temreconobj;

        }
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
                numobj = pos ;
                temnumobj = String.valueOf(numobj + 1);
                Intent IntentObj = new Intent();
                Bundle bundle = new Bundle();
                IntentObj.setClass(MainActivity.this, Main2Activity.class);
                bundle.putString("Num", temnumobj); /* 附加編號 */
                bundle.putString("Con",MemoStr[numobj]); /* 附加備忘項目的內容 */
                IntentObj.putExtras(bundle);
                MainActivity.this.startActivity(IntentObj);
                MainActivity.this.onDestory();
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
