package com.pra.haoye.gpsaddrepra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements LocationListener {

    /* 位置更新條件：5000 毫秒 */
    static final int MIN_TIME = 5000;
    /* 位置更新條件：5 公尺 */
    static final float MIN_DIST = 0;
    /* 定位管理員 */
    protected LocationManager mgrobj;
    protected TextView txvlocobj, txvsettingobj;
    protected Button buttonobj, getLocationObj, onQueryObj;

    /* GPS定位是否可用 */
    protected boolean isGPSEnabled;

    /* 網路定位是否可用 */
    protected boolean isNetworkEnabled;

    /* 儲存最近的定位資料 */
    protected Location myLocation;

    protected String locinfotem;

    /* 用來查詢地址的 Geocoder 物件 */
    protected Geocoder geocoder;

    /* 經緯度輸入欄位 */
    protected EditText edtLat,edtLon;

    /*是否已經向使用者要求過權限 */
    protected boolean permissionRequested = false;


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
        edtLat = (EditText) findViewById(R.id.edtLan);
        edtLon = (EditText) findViewById(R.id.edtLon);
        getLocationObj = (Button) findViewById(R.id.getLocation);
        onQueryObj = (Button) findViewById(R.id.onQuery);

        geocoder = new Geocoder(this, Locale.getDefault());

        /* 取得系統服務的 LocationManager 物件 */
        mgrobj = (LocationManager) getSystemService(LOCATION_SERVICE);

        /* 檢查若尚未授權, 則向使用者要求定位權限 */
        checkPermission();

        buttonobj.setOnClickListener(btnfunc);
        onQueryObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* "用經緯度查地址"鈕的On Click 事件 */
                String strLat = edtLat.getText().toString(); /* 取輸入的緯度字串 */
                String strLon = edtLon.getText().toString(); /* 取輸入的經度字串 */
                if(strLat.length() == 0 || strLon.length() == 0) /* 當字串為空白時 */
                    return; /* 結束處理 */

                txvlocobj.setText("讀取中...");
                double latitude = Double.parseDouble(strLat); /* 取得緯度值 */
                double longitude = Double.parseDouble(strLon); /* 取得經度值 */

                String strAddr = ""; /* 用來建立所要顯示的訊息字串 (地址字串) */
                try {
                    List<Address> listAddr = geocoder.
                            getFromLocation(latitude, longitude, /* 用經緯度查地址 */
                                    1); /* 只需傳回 1 筆地址資料 */

                    if (listAddr == null || listAddr.size() == 0) /* 檢查是否有取得地址 */
                        strAddr += "無法取得地址資料!";
                    else {
                        Address addr = listAddr.get(0);	/* 取 List 中的第一筆(也是唯一的一筆) */
                        for (int i = 0; i <= addr.getMaxAddressLineIndex(); i++)
                            strAddr += addr.getAddressLine(i) + "-";
                        if( addr.getCountryCode()!= null){
                            /* 查閱國碼 */
                            strAddr += addr.getCountryCode();
                            strAddr += ("\n" + locinfotem);
                        }
                    }
                } catch (Exception ex) {
                    strAddr += "取得地址發生錯誤:" + ex.toString();
                }
                txvlocobj.setText(strAddr);

            }
        });
        getLocationObj.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* "以手機位置當輸入"鈕的 On Click 事件 */
                        if(myLocation!=null){	/* 若位置物件非null */
                            edtLat.setText(Double.toString(		/* 將經度轉成字串 */
                                    myLocation.getLatitude()));
                            edtLon.setText(Double.toString(		/* 將緯度值轉成字串 */
                                    myLocation.getLongitude()));
                        }
                        else
                            txvlocobj.setText("無法取得定位資料！");
                    }
                }
        );

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
        myLocation = location;


        String str= "目前定位提供者 : " + location.getProvider();
        str+=String.format("\n緯度 : %s\n經度 : %s\n高度 : %.2f公尺",
                Location.convert(location.getLatitude(), Location.FORMAT_SECONDS),
                Location.convert(location.getLongitude(),location.FORMAT_SECONDS),
                location.getAltitude()
        );
                locinfotem = str;
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
