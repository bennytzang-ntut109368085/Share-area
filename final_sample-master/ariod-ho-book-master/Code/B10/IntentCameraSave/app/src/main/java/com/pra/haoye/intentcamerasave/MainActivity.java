package com.pra.haoye.intentcamerasave;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    protected Button camerabtnobj, clsbtnobj;
    protected ImageView cameraivobj;
    protected Uri imgUri;    /* 用來參照拍照存檔的 Uri 物件 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    protected void findViews() {
        cameraivobj = (ImageView)findViewById(R.id.civ);

        clsbtnobj = (Button)findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(recls);

        camerabtnobj = (Button)findViewById(R.id.camerabtn);
        camerabtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {

                        /* 建立動作為拍照的意圖 */
                        //Intent IntentObj = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        /* 啟動意圖並要求傳回資料 */
                        //startActivityForResult(IntentObj, 100);


                        if (ActivityCompat.checkSelfPermission( MainActivity.this, /* 檢查是否已取得寫入權限 */
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) {
                            /* 尚未取得權限 */
                            ActivityCompat.requestPermissions(MainActivity.this,  //向使用者要求允許寫入權限
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    200);
                        }
                        else {
                            /* 已經取得權限 */
                            savePhoto();  /* 拍照並存檔 */
                        }
                    }
                }
        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 200){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){ /* 使用者允許權限 */
                savePhoto();  /* 拍照並存檔 */
            } else { /* 使用者拒絕權限 */
                Toast.makeText(MainActivity.this,
                        "程式需要寫入權限才能運作",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == 100) {
            Bitmap bmp = null;

            try {

                /* 讀取圖檔內容轉換為 Bitmap 物件 */
                bmp = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(imgUri), null, null);
                Toast.makeText( MainActivity.this,
                        "照片 Uri : " + imgUri.toString(),
                        Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                Toast.makeText( MainActivity.this,
                        "無法讀取照片",
                        Toast.LENGTH_LONG).show();
            }

            /* 將 Bitmap 資料顯示在 ImageView 中 */
            cameraivobj.setImageBitmap(bmp);
        } else {
            Toast.makeText( MainActivity.this,
                    "沒有拍到照片",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void savePhoto() {
        imgUri =  getContentResolver().insert(  /* 透過內容資料庫新增一個圖片檔 */
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new ContentValues());
        Intent IntentObj = new Intent("android.media.action.IMAGE_CAPTURE"); /* 建立動作為拍照的 Intent */
        IntentObj.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);    /* 將 uri 加到拍照 Intent 的額外資料中 */
        startActivityForResult(IntentObj, 100);  /* 啟動 Intent 並要求傳回資料 */
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
