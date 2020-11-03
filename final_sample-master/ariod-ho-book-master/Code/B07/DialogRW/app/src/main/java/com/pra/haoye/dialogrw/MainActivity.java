package com.pra.haoye.dialogrw;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected Button clsbtnobj;
    protected TextView tvobj;

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
        tvobj = findViewById(R.id.tv);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setMessage("Do you like Android ?"); /* 設定顯示訊息 */
        dialog.setCancelable(false); /* 禁用返回鍵關閉交談窗 */
        dialog.setIcon(android.R.drawable.ic_menu_edit); /* 採用內建的圖示 */
        dialog.setTitle("Android Questionnaire"); /* 設定交談窗的標題 */

        /* 加入否定與肯定按鈕 */
        dialog.setPositiveButton("Like",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                tvobj.setText("Like Android !");
            }
        });

        dialog.setNegativeButton("Dislike",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                tvobj.setText("Dislike Android !");
            }
        });
        dialog.setNeutralButton("Other", null); /* 不監聽按鈕事件 */
        dialog.show(); /* 顯示交談窗 */
    }
}
