package com.pra.haoye.intentandroidservice;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    protected Button emailbtnobj, msgbtnobj, pagebtnobj,
            mapbtnobj, searchbtnobj, clsbtnobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    protected void findViews() {
        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(recls);

        emailbtnobj = findViewById(R.id.emailbtn);
        msgbtnobj = findViewById(R.id.msgbtn);
        pagebtnobj = findViewById(R.id.pagebtn);
        mapbtnobj = findViewById(R.id.mapbtn);
        searchbtnobj = findViewById(R.id.searchbtn);

        emailbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setAction(IntentObj.ACTION_VIEW);
                        IntentObj.setData(Uri.parse("mailto:xxxx1@xxxx1.com.tw"));
                        /* 設定副本收件人 */
                        IntentObj.putExtra(Intent.EXTRA_CC,
                                new String[] {"mailto:xxxx1@xxxx1.com.tw"});
                        /* 設定主旨 */
                        IntentObj.putExtra(Intent.EXTRA_SUBJECT, "Hello");
                        /* 設定內容 */
                        IntentObj.putExtra(Intent.EXTRA_TEXT, "Thank !");
                        startActivity(IntentObj);
                    }
                }
        );
        msgbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setAction(IntentObj.ACTION_VIEW);
                        /* 指定簡訊的傳送對象及內容 */
                        IntentObj.setData(Uri.parse("sms:0999-878787?body=Hi!"));
                        startActivity(IntentObj);
                    }
                }
        );
        pagebtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setAction(IntentObj.ACTION_VIEW);
                        /* 指定網址 */
                        IntentObj.setData(Uri.parse("https://github.com/"));
                        startActivity(IntentObj);
                    }
                }
        );
        mapbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setAction(IntentObj.ACTION_VIEW);
                        /* 指定 GPS 座標：台北車站 */
                        IntentObj.setData(Uri.parse("geo:25.047095,121.517308"));
                        startActivity(IntentObj);
                    }
                }
        );
        searchbtnobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setAction(IntentObj.ACTION_WEB_SEARCH);
                        /* Google Search */
                        IntentObj.putExtra(SearchManager.QUERY, "Github");
                        startActivity(IntentObj);
                    }
                }
        );
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
