package com.pra.haoye.seplayerpra;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class Main2Activity extends AppCompatActivity implements
        MediaPlayer.OnCompletionListener,
        SensorEventListener {

    /* 感測器管理員 */
    protected SensorManager smobj;
    /* 加速感測器物件 */
    protected Sensor srobj;
    /* 用來延遲體感控制的偵測間隔 */
    protected int delay = 0;

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

        /* 由系統服務取得感測器管理員 */
        smobj = (SensorManager) getSystemService(SENSOR_SERVICE);
        /* 取得加速感測器 */
        srobj = smobj.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onResume() { /* 當 Activity 啟動、或由暫停狀態回到互動狀態時 */
        super.onResume();
        /* 向加速感測器 (sr) 註冊監聽物件 */
        smobj.registerListener(Main2Activity.this, srobj, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() { /* 當 Activity 進入暫停狀態時 (例如切換到其他程式) */
        super.onPause();
        pos = vdv.getCurrentPosition(); /* 儲存播放位置 */
        /* 取消監聽物件(this) 的註冊 */
        smobj.unregisterListener(this);
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

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x, y, z;
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        if(Math.abs(x) < 1 && Math.abs(y) < 1 && z < -9) { /* 如果手機面朝下平放 */
            if(vdv.isPlaying()) { /* 如果正在播放, 就要暫停 */
                vdv.pause();
            }
        } else {
            if(delay > 0) { /* delay大於 0 時, 表示要略過這次的偵測 */
                delay--; /* 將次數減 1, 直到 0 為止 */
            } else {
                if(Math.abs(x) + Math.abs(y) + Math.abs(z) > 32) { /* 加速度總合超過 32 */
                    if(vdv.isPlaying()){
                        vdv.pause();
                    } else {
                        vdv.start();
                    }
                    delay = 5; /* 延遲  5  次不偵測 (約 1 秒) */
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}