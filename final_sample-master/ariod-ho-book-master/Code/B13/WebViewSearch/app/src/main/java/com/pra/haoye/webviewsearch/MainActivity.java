package com.pra.haoye.webviewsearch;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    protected WebView wvobj;
    protected ProgressBar pbobj;
    protected EditText keytextobj;
    protected Button sbtnobj;
    protected String keywordobj; /* 用來記錄關鍵字 */
    protected String baseURLobj = "https://www.google.com/search?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();

    }
    protected void Func() {
        wvobj = (WebView) findViewById(R.id.show2v);
        pbobj = (ProgressBar) findViewById(R.id.pb);
        keytextobj = (EditText)findViewById(R.id.searchev);
        sbtnobj = (Button)findViewById(R.id.searchbt);
        wvobj.getSettings().setJavaScriptEnabled(true); /* 啟用 JavaScript */
        wvobj.setWebViewClient(new WebViewClient()); /* 建立及使用 WebViewClient 物件 */
        wvobj.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                pbobj.setProgress(progress);       //設定進度
                pbobj.setVisibility(progress < 100 ? View.VISIBLE : View.GONE); /* 依進度來讓進度條顯示或消失 */
            }
        });
        sbtnobj.setOnClickListener(seabtnfunc);

    }

    private View.OnClickListener seabtnfunc = new View.OnClickListener(){
        public void onClick(View v){
            keywordobj = keytextobj.getText().toString().replaceAll("\\s+", "+"); /* 將字串中的單一或連續空白置換成 + */
            wvobj.loadUrl(baseURLobj + keywordobj);
        }
    };


    @Override
    public void onBackPressed() { /* 按下返回鍵時的事件處理 */
        if(wvobj.canGoBack()){ /* 如果 WebView 有上一頁 */
            wvobj.goBack(); /* 回上一頁 */
            return;
        }
        super.onBackPressed();  /* 呼叫父類別的同名方法, 以執行預設動作(結束程式) */
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("關鍵字", keywordobj); /* 儲存目前的查詢參數 */
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences myPref = getPreferences(MODE_PRIVATE);
        keywordobj = myPref.getString("關鍵字", "Taipei+101");
        if(wvobj.getUrl()==null){
            wvobj.loadUrl(baseURLobj+keywordobj);
        }
    }
}
