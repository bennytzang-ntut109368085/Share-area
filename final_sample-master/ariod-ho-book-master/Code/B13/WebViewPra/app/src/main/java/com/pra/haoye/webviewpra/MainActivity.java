package com.pra.haoye.webviewpra;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
    protected WebView show2vobj;
    protected ProgressBar pbobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();
    }
    protected void Func() {

        show2vobj = (WebView) findViewById(R.id.show2v);
        pbobj = (ProgressBar) findViewById(R.id.pb);

        // show2vobj.setInitialScale(60);

        /* 啟用 JavaScript */
        show2vobj.getSettings().setJavaScriptEnabled(true);
        /* 啟用縮放功能 */
        show2vobj.getSettings().setBuiltInZoomControls(true);
        /* 顯示縮放 */
        show2vobj.invokeZoomPicker();
        /* 建立及使用 WebViewClient 物件  */
        show2vobj.setWebViewClient(new WebViewClient());
        show2vobj.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                /* 設定進度 */
                pbobj.setProgress(progress);
                /* 依進度來讓進度條顯示或消失 */
                pbobj.setVisibility(progress < 100? View.VISIBLE: View.GONE);
            }
        });
        /* Github */
        show2vobj.loadUrl("https://github.com/");
    }

    @Override
    public void onBackPressed() {
        /* 如果 WebView 有上一頁 */
        if(show2vobj.canGoBack()){
            /* 回上一頁 */
            show2vobj.goBack();
            return;
        }
        /* 呼叫父類別的同名方法, 以執行預設動作(結束程式) */
        super.onBackPressed();
    }

}
