package com.pra.haoye.gpspra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    /* 位置更新條件：5000 毫秒 */
    static final int MIN_TIME = 5000;
    /* 位置更新條件：5 公尺 */
    static final float MIN_DIST = 0;
    /* 定位管理員 */
    protected LocationManager mgrobj;
    protected TextView txvlocobj, txvsettingobj;
    protected Button buttonobj;

    /* GPS定位是否可用 */
    protected boolean isGPSEnabled;

    /* 網路定位是否可用 */
    protected boolean isNetworkEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();
    }

    protected void Func() {
        txvlocobj = (TextView) findViewById(R.id.txvlocobj);
        txvsettingobj = (TextView) findViewById(R.id.txvsetting);
        buttonobj = (Button) findViewById(R.id.button);

        /* 取得系統服務的 LocationManager 物件 */
        mgrobj = (LocationManager) getSystemService(LOCATION_SERVICE);

        /* 檢查若尚未授權, 則向使用者要求定位權限 */
        checkPermission();

        buttonobj.setOnClickListener(btnfunc);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /* 關閉定位更新功能 */
        enableLocationUpdates(false);
    }


    /* 檢查若尚未授權, 則向使用者要求定位權限 */
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* 清除之前的定位資訊 */
        txvlocobj.setText("尚未取得定位資訊");
        /* 開啟定位更新功能 */
        enableLocationUpdates(true);

        String str="GPS定位:"+ (isGPSEnabled?"開啟":"關閉");
        str += "\n網路定位:"+ (isNetworkEnabled?"開啟":"關閉");

        /* 顯示 GPS 與網路定位是否可用 */
        txvsettingobj.setText(str);

    }
    private View.OnClickListener btnfunc = new View.OnClickListener(){
        public void onClick(View v){
            Intent it =	/* 使用 Intent 物件啟動 "定位" 設定程式 */
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(it);
        }
    };
    @Override
    public void onLocationChanged(Location location) {
        /* 位置變更事件 */
        String str="目前定位提供者:"+location.getProvider();
        str+=String.format("\n緯度 : %s\n經度 : %s\n高度 : %.2f公尺",
                Location.convert(location.getLatitude(), Location.FORMAT_SECONDS),
                Location.convert(location.getLongitude(),location.FORMAT_SECONDS),
                location.getAltitude()
        );
        /*
        str += String.format("\n緯度:%.6f\n經度:%.6f\n高度:%.2f公尺",
                location.getLatitude(), // 緯度
                location.getLongitude(), // 經度
                location.getAltitude()); // 高度
                */
        txvlocobj.setText(str);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /* 開啟或關閉定位更新功能 */
    private void enableLocationUpdates(boolean isTurnOn) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) { /* 使用者已經允許定位權限 */
            if (isTurnOn) {
                /* 檢查 GPS 與網路定位是否可用 */
                isGPSEnabled = mgrobj.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = mgrobj.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    /* 無提供者, 顯示提示訊息 */
                    Toast.makeText(this, "請確認已開啟定位功能!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "取得定位資訊中...", Toast.LENGTH_LONG).show();
                    if (isGPSEnabled)
                        mgrobj.requestLocationUpdates( /* 向 GPS 定位提供者註冊位置事件監聽器 */
                                LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, this);
                    if (isNetworkEnabled)
                        mgrobj.requestLocationUpdates( /* 向網路定位提供者註冊位置事件監聽器 */
                                LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, this);
                }
            }
            else {
                mgrobj.removeUpdates(this); /* 停止監聽位置事件 */
            }
        }
    }
}
