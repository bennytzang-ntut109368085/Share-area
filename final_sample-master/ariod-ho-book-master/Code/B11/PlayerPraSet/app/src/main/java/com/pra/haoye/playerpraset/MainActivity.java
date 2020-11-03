package com.pra.haoye.playerpraset;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
        MediaPlayer.OnPreparedListener, /* 實作 MediaPlayer 的 3 個的事件監聽介面 */
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    /* 儲存影音檔案的 Uri */
    protected Uri uri;

    /* 參照到畫面中的元件 */
    protected TextView showFilmPathTVobj, showSongNameTVobj, clsbtnobj;
    protected Button songbtnobj, filmbtnobj, playbtnobj,
            stopbtnobj, igbBackwardobj, igbForwardobj;
    protected CheckBox repeatcbobj;


    /* 用來參照 MediaPlayer 物件 */
    protected MediaPlayer mper;

    /* 用來參照 Toast 物件 (顯示訊息之用) */
    protected Toast tos;

    /* 記錄是否為影片檔 */
    protected boolean isVideo = false;

    private View.OnClickListener recls = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.onDestory();
        }
    };
    private void onDestory() {
        /* 釋放 MediaPlayer 物件 */
        mper.release();
        super.onDestroy();
        System.exit(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mper.isPlaying()) {  /* 如果正在播, 就暫停 */
            playbtnobj.setText("Play");
            mper.pause();  /* 暫停播放 */
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsFunc();
        setReqOrientationFunc();
        OnClickListenerFunc();
    }

    protected void setReqOrientationFunc() {
        /* 設定螢幕不隨手機旋轉 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        /* 設定螢幕直向顯示 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void findViewsFunc() {
        clsbtnobj = (Button)findViewById(R.id.clsbtn);
        songbtnobj = (Button)findViewById(R.id.songbtn);
        filmbtnobj = (Button)findViewById(R.id.filmbtn);
        showFilmPathTVobj = (TextView)findViewById(R.id.showFilmPathTV);
        showSongNameTVobj = (TextView)findViewById(R.id.showSongNameTV);
        playbtnobj = (Button)findViewById(R.id.playbtn);
        stopbtnobj = (Button)findViewById(R.id.stopbtn);
        repeatcbobj = (CheckBox)findViewById(R.id.repeatcb);
        igbBackwardobj = (Button)findViewById(R.id.igbBackward);
        igbForwardobj = (Button)findViewById(R.id.igbForward);
        igbBackwardobj.setText("<<");
        igbForwardobj.setText(">>");
    }

    protected void OnClickListenerFunc() {

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
        playbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        /* 按下 "播放" 鈕時 */
                        if (mper.isPlaying()) {  /* 如果正在播, 就暫停 */
                            mper.pause();   /* 暫停播放 */
                            playbtnobj.setText("Play");
                        } else {  /* 如果沒有在播, 就開始播 */
                            mper.start();   /* 開始播放 */
                            playbtnobj.setText("Pause");
                            stopbtnobj.setEnabled(true);
                        }
                    }
                }
        );
        stopbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        /* 按下 "停止" 鈕時 */
                        mper.pause(); /* 暫停播放 */
                        mper.seekTo(0); /* 移到音樂中 0 秒的位置 */
                        playbtnobj.setText("Play");
                        stopbtnobj.setEnabled(false);
                    }
                }
        );
        repeatcbobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        /* 按下 "重複播放" 多選鈕時 */
                        if (repeatcbobj.isChecked()) {
                            mper.setLooping(true); /* 設定要重複播放 */
                        } else {
                            mper.setLooping(false); /* 設定不要重複播放 */
                        }

                    }
                }
        );
        igbForwardobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        /* 按下前進圖形鈕時 */
                        if(!playbtnobj.isEnabled()) return; /* 如果還沒準備好(播放鈕不能按), 則不處理 */
                        int len = mper.getDuration(); /* 讀取音樂長度 */
                        int pos = mper.getCurrentPosition(); /* 讀取目前播放位置 */
                        pos += 10000; /* 前進 10 秒 (10000ms) */
                        if(pos > len) pos = len; /* 不可大於總秒數 */
                        mper.seekTo(pos); /* 移動播放位置 */
                        tos.setText("前進 10 秒：" + pos/1000 + "/" + len/1000); /* 顯示訊息 */
                        tos.show();
                    }
                }
        );
        igbBackwardobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        /* 按下倒退圖形鈕時 */
                        if(!playbtnobj.isEnabled()) return; /* 如果還沒準備好(播放鈕不能按), 則不處理 */
                        int len = mper.getDuration(); /* 讀取音樂長度 */
                        int pos = mper.getCurrentPosition(); /* 讀取目前播放位置 */
                        pos -= 10000; /* 倒退 10 秒 (10000ms) */
                        if(pos <0) pos = 0; /* 不可小於 0 */
                        mper.seekTo(pos); /* 移動播放位置 */
                        tos.setText("倒退 10 秒：" + pos/1000 + "/" + len/1000); /* 顯示訊息 */
                        tos.show();
                    }
                }
        );

        /* 建立 MediaPlayer 物件 */
        mper = new MediaPlayer();
        /* 設定 3 個事件監聽器 */
        mper.setOnPreparedListener(this);
        mper.setOnErrorListener(this);
        mper.setOnCompletionListener(this);
        /* 建立 Toast 物件 */
        tos = Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);

        /* 準備播放指定的影音檔 */
        prepareMedia();
    }

    void prepareMedia() {
        playbtnobj.setText("Play"); /* 將按鈕文字恢復為 "播放" */
        playbtnobj.setEnabled(false); /* 使播放鈕不能按 (要等準備好才能按) */
        stopbtnobj.setEnabled(false); /* 使停止鈕不能按 */
        try {
            mper.reset(); /* 如果之前有播過, 必須 reset 後才能更換 */
            mper.setDataSource(this, uri);  /* 指定影音檔來源 */
            mper.setLooping(repeatcbobj.isChecked()); /* 設定是否重複播放 */
            mper.prepareAsync(); /* 要求 MediaPlayer 準備播放指定的影音檔 */
        } catch (Exception e) { /* 攔截錯誤並顯示訊息 */
            tos.setText("指定影音檔錯誤" + e.toString());
            tos.show();
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
            /* 重新準備播放剛選擇的影音檔 */
            prepareMedia();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mper.seekTo(0); /* 將播放位置歸 0 */
        playbtnobj.setText("Play"); /* 讓播放鈕顯示 "播放" */
        stopbtnobj.setEnabled(false); /* 讓停止鈕不能按 */
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        tos.setText("發生錯誤，停止播放");  /* 顯示錯誤訊息 */
        tos.show();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playbtnobj.setEnabled(true);
        /* 當準備好時, 只需讓 "播放" 鈕可以按即可 */
    }
}

