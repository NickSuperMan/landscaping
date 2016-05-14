package com.zua.landscaping.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.zua.landscaping.R;
import com.zua.landscaping.utils.TitleBuilder;


public class WebActivity extends Activity {

    private ProgressDialog mProgressDialog;
    private ImageButton mWebBack;
    private ImageButton mWebForward;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        String name = getIntent().getStringExtra("name");

        new TitleBuilder(this).setTitleText(name).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();

        final WebView webView = (WebView) findViewById(R.id.web_view);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 50) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try {
                    if (mProgressDialog == null) {
                        mProgressDialog = ProgressDialog.show(WebActivity.this, null, getString(R.string.loading));
                        mProgressDialog.setCancelable(true);
                    } else {
                        if (!mProgressDialog.isShowing()) {
                            mProgressDialog.show();
                        }
                    }
                } catch (WindowManager.BadTokenException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                if (webView.canGoBack()) {
                    mWebBack.setEnabled(true);
                    mWebBack.setImageResource(R.drawable.web_back);
                } else {
                    mWebBack.setEnabled(false);
                    mWebBack.setImageResource(R.drawable.web_back_disable);
                }

                if (webView.canGoForward()) {
                    mWebForward.setEnabled(true);
                    mWebForward.setImageResource(R.drawable.web_forward);
                } else {
                    mWebForward.setEnabled(false);
                    mWebForward.setImageResource(R.drawable.web_forward_disable);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });


        String url = "";
        if (getIntent().getStringExtra("url") != null) {
            url = getIntent().getStringExtra("url");
        }

        webView.loadUrl(url);

        mWebBack = (ImageButton) findViewById(R.id.web_back);
        mWebBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });

        mWebForward = (ImageButton) findViewById(R.id.web_forward);
        mWebForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goForward();
            }
        });

        ImageButton webReload = (ImageButton) findViewById(R.id.web_refresh);
        webReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (null != mProgressDialog && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }
}