package com.pra.haoye.webviewpratest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {

    protected Button btnGoogleobj , btnGithubobj, btnFBobj;
    protected WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if ((title != null) && (title.trim().length() != 0)) {
                setTitle(title);
            }
        }
    };

    protected void Func(){

        mWebView = (WebView)findViewById(R.id.webView);
        btnGoogleobj = (Button) findViewById(R.id.btnGoogle);
        btnGithubobj = (Button) findViewById(R.id.btnGithub);
        btnFBobj = (Button) findViewById(R.id.btnFB);

        /* WebViewClient 與 WebChromeClient 兩個都要用才可以完整使用全部功能 */

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.loadUrl("https://github.com");
        
        btnGoogleobj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mWebView.loadUrl("http://ww.google.com.tw");
            }
        });
        btnGithubobj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mWebView.loadUrl("https://github.com");
            }
        });
        btnFBobj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mWebView.loadUrl("https://www.facebook.com/");
            }
        });

    }

}
