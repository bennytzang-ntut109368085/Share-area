package com.pra.haoye.intentcamerapicsize;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    protected Button camerabtnobj, clsbtnobj;
    protected ImageView cameraivobj;
    protected Uri imgUri;    /* 用來參照拍照存檔的 Uri 物件 */
    private int iw, ih, vw, vh;

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

                        if (ActivityCompat.checkSelfPermission( MainActivity.this, /* 檢查是否已取得寫入權限 */
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) {
                            /* 尚未取得權限 */
                            ActivityCompat.requestPermissions(MainActivity.this,  /* 向使用者要求允許寫入權限 */
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

            showImgFunc();

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


    void showImgFunc() {
        /* 建立選項物件 */
        BitmapFactory.Options option = new BitmapFactory.Options();
        /* 設定選項：只讀取圖檔資訊而不載入圖檔 */
        option.inJustDecodeBounds = true;

        /* 讀取圖檔資訊存入 Option 中 */
        try {
            BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri), null, option);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "讀取照片資訊時發生錯誤", Toast.LENGTH_LONG).show();
            return;
        }

        /* 由 option 中讀出圖檔寬度 */
        iw = option.outWidth;

        /* 由 option 中讀出圖檔高度 */
        ih = option.outHeight;

        /* 取得 ImageView 的寬度 */
        vw = cameraivobj.getWidth();

        /* 取得 ImageView 的高度 */
        vh = cameraivobj.getHeight();

        /* 計算縮小比率 */
        int scaleFactor = Math.min(iw/vw, ih/vh);

        /* 關閉只載入圖檔資訊的選項 */
        option.inJustDecodeBounds = false;

        /* 設定縮小比例, 例如 2 則長寬都將縮小為原來的 1/2 */
        option.inSampleSize = scaleFactor;

        /* 載入圖檔 */
        Bitmap bmp = null;
        try {
            /* 讀取圖檔內容轉換為 Bitmap 物件 */
            bmp = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(imgUri), null, option);

        } catch (IOException e) {
            Toast.makeText( MainActivity.this,
                    "無法取得照片",
                    Toast.LENGTH_LONG).show();
        }
        /* 將 Bitmap 資料顯示在 ImageView 中 */
        cameraivobj.setImageBitmap(bmp);

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("圖檔資訊")
                .setMessage("圖檔 URI : " + imgUri.toString() +
                        "\n原始尺寸 : " + iw + "x" + ih +
                        "\n載入尺寸 : " + bmp.getWidth() + "x" + bmp.getHeight() +
                        "\n顯示尺寸 : " + vw + "x" + vh
                )
                .setNeutralButton("關閉",null)
                .show();
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
