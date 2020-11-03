package com.pra.haoye.intentplayerpra;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    /* 儲存影音檔案的 Uri */
    protected Uri uri;

    /* 參照到畫面中的元件 */
    protected TextView showFilmPathTVobj, showSongNameTVobj, clsbtnobj;

    protected Button songbtnobj, filmbtnobj;

    /* 記錄是否為影片檔 */
    protected boolean isVideo = false;


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
        clsbtnobj = (Button)findViewById(R.id.clsbtn);
        songbtnobj = (Button)findViewById(R.id.songbtn);
        filmbtnobj = (Button)findViewById(R.id.filmbtn);
        showFilmPathTVobj = (TextView)findViewById(R.id.showFilmPathTV);
        showSongNameTVobj = (TextView)findViewById(R.id.showSongNameTV);

        /* 預設會播放程式內的音樂檔 */
        /* 檔名命名不建議有大寫英文 */
        uri = Uri.parse("android.resource://" +
                getPackageName() + "/" + R.raw.junitaisen_01);
        /* 在畫面中顯示檔名 */
        showSongNameTVobj.setText("junitaisen_01.mp3");

        /* 顯示 Uri */
        showFilmPathTVobj.setText("程式內的樂曲 - " + uri.toString());

        clsbtnobj.setOnClickListener(recls);

        songbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                        /* 要選取所有音樂類型 */
                        it.setType("audio/*");
                        startActivityForResult(it, 100);
                    }
                }
        );
        filmbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                        /* 要選取所有影片類型 */
                        it.setType("video/*");
                        startActivityForResult(it, 101);
                    }
                }
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            /* 記錄是否選取了影片檔 (當識別碼為101時) */
            isVideo = (requestCode == 101);

            /* 取得選取檔案的 Uri */
            uri = data.getData();

            /* 顯示檔名 */
            showSongNameTVobj.setText( (isVideo? "[ Film ] " : "[ Song ]") + getFilename(uri));

            /* 顯示檔案的 URI */
            showFilmPathTVobj.setText("URI - " + uri.toString());
        }
    }

    /* 以 URL 向內容資料庫查詢檔名 */
    String getFilename(Uri uri) {
        String fileName = null;

        /* 宣告要查詢的欄位 */
        String[] colName = {MediaStore.MediaColumns.DISPLAY_NAME};

        /* 以 uri 進行查詢 */
        Cursor cursor = getContentResolver().query(uri, colName,
                null, null, null);

        /* 移到查詢結果的第一筆記錄 */
        cursor.moveToFirst();
        fileName = cursor.getString(0);

        /* 關閉查詢結果 */
        cursor.close();

        /* 傳回檔名 */
        return fileName;
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
