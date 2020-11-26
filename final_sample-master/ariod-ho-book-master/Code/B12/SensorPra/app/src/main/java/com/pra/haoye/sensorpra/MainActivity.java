package com.pra.haoye.sensorpra;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    /* 用來參照畫面中的文字元件 */
    protected TextView showtvobj;
    /* 用來參照 "感測器管理員" */
    protected SensorManager smobj;
    /* 用來參照 "加速感測器物件" */
    protected Sensor srobj;
    TextView txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();
    }

    protected void Func() {
        /* 由系統服務取得感測器管理員 */
        smobj = (SensorManager) getSystemService(SENSOR_SERVICE);

        /* 取得加速感測器 */
        srobj = smobj.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /* 取得TextView元件 */
        showtvobj = (TextView) findViewById(R.id.showtv);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        showtvobj.setText(String.format("X 軸 : %1.2f\nY 軸 : %1.2f\nZ 軸 : %1.2f",
                event.values[0], event.values[1], event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        /* 向加速感測器 (sr) 註冊監聽物件(this) */
        smobj.registerListener(MainActivity.this, srobj, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        /* 取消監聽物件(this) 的註冊 */
        smobj.unregisterListener(MainActivity.this);
    }
}
