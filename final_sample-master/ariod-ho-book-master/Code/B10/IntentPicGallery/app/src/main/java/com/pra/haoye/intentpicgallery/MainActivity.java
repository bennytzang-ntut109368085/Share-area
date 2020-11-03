package com.pra.haoye.intentpicgallery;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    protected Button camerabtnobj, clsbtnobj, sharebtnobj, gallerybtnobj;
    protected ImageView cameraivobj;

    /* 用來參照拍照存檔的 Uri 物件 */
    protected Uri imgUri;
    private int iw, ih, vw, vh;

    /* 用來儲存是否需要旋轉 */
    private boolean needRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setReqOrientation();
    }

    protected void setReqOrientation(){
        /* 設定螢幕不隨手機旋轉 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        /* 設定螢幕直向顯示 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void findViews() {

        cameraivobj = (ImageView)findViewById(R.id.civ);
        clsbtnobj = (Button)findViewById(R.id.clsbtn);
        sharebtnobj = (Button)findViewById(R.id.sharebtn);
        gallerybtnobj = (Button)findViewById(R.id.gallerybtn);
        camerabtnobj = (Button)findViewById(R.id.camerabtn);

        clsbtnobj.setOnClickListener(recls);

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

        gallerybtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {

                        /* 動作設為 "選取內容" */
                        Intent IntentObj = new Intent(Intent.ACTION_GET_CONTENT);

                        /* 設定要選取的媒體類型為：所有類型的圖片 */
                        IntentObj.setType("image/*");

                        /* 啟動意圖, 並要求傳回選取的圖檔 */
                        startActivityForResult(IntentObj, 101);

                    }
                }
        );

        sharebtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        if(imgUri != null){
                            Intent IntentObj = new Intent(Intent.ACTION_SEND);
                            IntentObj.setType("image/*");
                            IntentObj.putExtra(Intent.EXTRA_STREAM, imgUri);
                            startActivity(IntentObj);
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

            /* 要求的意圖成功了 */
        if( resultCode == Activity.RESULT_OK) {
            switch(requestCode) {

                /* 拍照 */
                case 100:

                    /* 設為系統共享媒體檔 */
                    Intent IntentObj = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imgUri);
                    sendBroadcast(IntentObj);
                    break;

                /* 選取相片 */
                case 101:
                    /* 取得選取相片的 Uri */
                    imgUri = data.getData();
                    break;
            }
            /* 顯示 imgUri 所指明的相片 */
            showImgFunc();
        } else {
            Toast.makeText( MainActivity.this,
                    requestCode == 100? "沒有拍到照片" : "沒有選取相片",
                    Toast.LENGTH_LONG).show();
        }
    }
    private void savePhoto() {

        /* 透過內容資料庫新增一個圖片檔 */
        imgUri =  getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new ContentValues());

        /* 建立動作為拍照的 Intent */
        Intent IntentObj = new Intent("android.media.action.IMAGE_CAPTURE");

        /* 將 uri 加到拍照 Intent 的額外資料中 */
        IntentObj.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

        /* 啟動 Intent 並要求傳回資料 */
        startActivityForResult(IntentObj, 100);
    }
    void showImgFunc() {

        /* 建立選項物件 */
        BitmapFactory.Options option = new BitmapFactory.Options();

        /* 設定選項：只讀取圖檔資訊而不載入圖檔 */
        option.inJustDecodeBounds = true;

        /* 讀取圖檔資訊存入 Option 中 */
        try {
            BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(imgUri), null, option);
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
        int scaleFactor;

        /* 如果圖片的寬度小於高度 */
        if(iw < ih) {

            /* 不需要旋轉 */
            needRotate = false;

            /* 計算縮小比率 */
            scaleFactor = Math.min(iw/vw, ih/vh);

        } else {

            /* 需要旋轉 */
            needRotate = true;

            /* 將 ImageView 的寬、高互換來計算縮小比率 */
            scaleFactor = Math.min(iw/vh, ih/vw);
        }

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

        /* 如果需要旋轉 */
        if(needRotate) {
            /* 建立 Matrix 物件 */
            Matrix matrix = new Matrix();

            /* 設定旋轉角度 */
            matrix.postRotate(90);

            /* 用原來的 Bitmap 產生一個新的 Bitmap */
            bmp = Bitmap.createBitmap(bmp ,
                    0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("圖檔資訊")
                .setMessage("圖檔 URI : " + imgUri.toString() +
                        "\n原始尺寸 : " + iw + "x" + ih +
                        "\n載入尺寸 : " + bmp.getWidth() + "x" + bmp.getHeight() +
                        "\n顯示尺寸 : " + vw + "x" + vh + (needRotate?" (Rotate)" : "")
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

