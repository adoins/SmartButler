package com.liuguilin.butlerservice.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.liuguilin.butlerservice.R;

/**
 * 页面跳转网页
 * Created by LGL on 2016/3/25.
 */
public class WebViewActivity extends BaseActivity {

    private ProgressBar pb;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();
    }

    private void initView() {

        Bundle bundle = getIntent().getExtras();
        final String title = bundle.getString("title");
        String url = bundle.getString("url");

        getSupportActionBar().setTitle(title);

        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setMax(100);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebViewClient());
        webView.loadUrl(url);

        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
            }
        });

    }

    /**
     * 接口回调
     */
    public class WebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pb.setProgress(newProgress);
            if (newProgress == 100) {
                pb.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
