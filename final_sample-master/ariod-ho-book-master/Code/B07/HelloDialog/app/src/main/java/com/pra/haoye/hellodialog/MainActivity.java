package com.pra.haoye.hellodialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    protected Button clsbtnobj;

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
        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(returnBtn);
        AlertDialog.Builder bdr = new AlertDialog.Builder(this);
        bdr.setMessage("Hello, Android !"); /* 加入文字訊息 */
        bdr.setTitle("Hi !"); /* 加入標題 */
        bdr.setIcon(R.mipmap.ic_launcher); /* 加入圖示 */
        bdr.setCancelable(true);   /* 允許按返回鍵關閉交談窗 */
        bdr.show();

    }
}
