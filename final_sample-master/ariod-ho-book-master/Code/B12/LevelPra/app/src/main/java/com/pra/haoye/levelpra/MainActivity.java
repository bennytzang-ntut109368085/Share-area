package com.pra.haoye.levelpra;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /* 用來參照 "感測器管理員" */
    protected SensorManager smobj;
    /* 用來參照 "加速感測器物件" */
    protected Sensor srobj;
    /* 用來參照畫面中的文字元件 */
    protected TextView showtvobj;
    /* 用來參照畫面中要移動的小圖 */
    protected ImageView igvobj;
    /* 用來參照畫面的 Layout 元件 */
    protected RelativeLayout layout;
    /* 用來儲存 x,y 方向每一等分的大小 */
    protected double mx = 0, my = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();
    }

    protected void Func() {
        /* 設定螢幕不隨手機旋轉 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        /* 由系統服務取得感測器管理員 */
        smobj = (SensorManager) getSystemService(SENSOR_SERVICE);
        /* 取得加速感測器 */
        srobj = smobj.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        /* 取得 TextView 元件 */
        showtvobj = (TextView) findViewById(R.id.showtv);
        /* 取得要移動的ImageView元件 */
        igvobj = (ImageView) findViewById(R.id.moveigv);
        /* 取得 layout 元件 */
        layout = (RelativeLayout) findViewById(R.id.layout);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(mx == 0) { /* 如果還沒計算過 */
            mx = (layout.getWidth()-igvobj.getWidth()) /20.0; /* 計算 x 方向每一等分的大小 */
            my = (layout.getHeight()-igvobj.getHeight()) /20.0; /* 計算 y 方向每一等分的大小 */
        }

        /* 取得小圖的 LayoutParm 物件 */
        RelativeLayout.LayoutParams parms =
                (RelativeLayout.LayoutParams) igvobj.getLayoutParams();

        /* 設定左邊界 */
        parms.leftMargin = (int)((10-event.values[0]) * mx);
        /* 設定上邊界 */
        parms.topMargin = (int)((10+event.values[1]) * my);
        /* 將小圖套用 LayoutParm, 使邊界設定生效 */
        igvobj.setLayoutParams(parms);

        /*  顯示感測器的資料 */
        showtvobj.setText(String.format("X 軸 : %1.2f\nY 軸 : %1.2f\nZ 軸 : %1.2f",
                event.values[0], event.values[1], event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        /* 向加速感測器 (sr) 註冊監聽物件(this) */
        super.onResume();
        smobj.registerListener(this, srobj, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        /* 取消監聽物件(this) 的註冊 */
        super.onPause();
        smobj.unregisterListener(this);
    }
}
