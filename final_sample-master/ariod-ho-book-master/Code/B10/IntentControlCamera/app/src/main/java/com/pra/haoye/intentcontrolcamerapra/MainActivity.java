package com.pra.haoye.intentcontrolcamerapra;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    protected Button camerabtnobj, clsbtnobj;
    protected ImageView cameraivobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    protected void findViews() {

        cameraivobj = findViewById(R.id.civ);

        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(recls);

        camerabtnobj = findViewById(R.id.camerabtn);
        camerabtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {

                        /* 建立動作為拍照的意圖 */
                        Intent IntentObj = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        /* 啟動意圖並要求傳回資料 */
                        startActivityForResult(IntentObj, 100);
                    }
                }
        );

    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode==100) {

            /* 將 Intent 的附加資料轉為 Bundle 物件 */
            Bundle extras = data.getExtras();

            /* 由 Bundle 取出名為 "data" 的 Bitmap 資料 */
            Bitmap bmp = (Bitmap) extras.get("data");

            /* 將 Bitmap 資料顯示在 ImageView 中 */
            cameraivobj.setImageBitmap(bmp);
            
        } else {
            Toast.makeText( MainActivity.this,
                    "沒有拍到照片",
                    Toast.LENGTH_LONG).show();
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
