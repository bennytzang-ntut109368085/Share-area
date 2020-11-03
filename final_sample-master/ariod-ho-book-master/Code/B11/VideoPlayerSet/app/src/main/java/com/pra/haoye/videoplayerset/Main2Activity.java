package com.pra.haoye.videoplayerset;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class Main2Activity extends AppCompatActivity implements
        MediaPlayer.OnCompletionListener {

    VideoView vdv;  /* 用來參照 VideoView 物件 */
    int pos = 0;    /* 用來記錄前次的播放位置 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); /* 隱藏系統的狀態列 */
        getSupportActionBar().hide();           /* 隱藏標題列 */
        setContentView(R.layout.activity_main2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);/* 保持螢幕一直開著 (不要自動休眠) */
        Intent it = getIntent();     /* 取得傳入的 Intent 物件 */
        Uri uri = Uri.parse(it.getStringExtra("uri")); /* 取出要播放影片的 Uri */
        if(savedInstanceState != null) {               /* 如果是因旋轉而重新啟動 Activity */
            pos = savedInstanceState.getInt("pos", 0); /* 取出旋轉前所儲存的播放位置 */
        }
        vdv = (VideoView)findViewById(R.id.videoView);		/* 參照到畫面中的 VideoView 元件 */
        MediaController mediaCtrl = new MediaController(this); /* 建立播放控制物件 */
        vdv.setMediaController(mediaCtrl);  /* 設定要用播放控制物件來控制播放 */
        vdv.setVideoURI(uri);   /* 設定要播放影片的 Uri */
        vdv.setOnCompletionListener(this);
    }

    @Override
    protected void onResume() { /* 當 Activity 啟動、或由暫停狀態回到互動狀態時 */
        super.onResume();
    }

    @Override
    protected void onPause() { /* 當 Activity 進入暫停狀態時 (例如切換到其他程式) */
        super.onPause();
        pos = vdv.getCurrentPosition(); /* 儲存播放位置 */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", pos); /* 將暫停時所取得的目前播放位置儲存起來 */
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        vdv.seekTo(pos); /* 移到 pos 的播放位置 */
        vdv.start(); /* 開始播放 */
    }

    @Override
    protected void onStop() {
        super.onStop();
        vdv.stopPlayback(); /* 停止播放 */
    }

}
