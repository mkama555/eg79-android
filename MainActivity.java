package com.eg79.app;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.*;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.net.Uri;

public class MainActivity extends Activity {
    private static final String HOME_URL = "https://eg79vh34.com/main/inicio?ch=8550&_ts===gMyUiMzkzN2cjM2UzM5cTMyITJ";
    private WebView webView;
    private ProgressBar progressBar;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.WHITE);
        webView = new WebView(this);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(100);
        root.addView(webView, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout.LayoutParams pp = new FrameLayout.LayoutParams(-1, dp(3));
        root.addView(progressBar, pp);
        setContentView(root);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setSupportZoom(false);
        s.setAllowFileAccess(false);
        s.setAllowContentAccess(false);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public void onProgressChanged(WebView v, int p) {
                progressBar.setProgress(p);
                progressBar.setVisibility(p >= 100 ? View.GONE : View.VISIBLE);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                Uri u = r.getUrl(); String h = u.getHost(); String scheme = u.getScheme();
                if ("https".equalsIgnoreCase(scheme) && h != null &&
                    (h.equals("eg79vh34.com") || h.endsWith(".eg79vh34.com"))) return false;
                if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                    try { startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, u)); }
                    catch (Exception ignored) {}
                }
                return true;
            }
        });
        if (state != null) webView.restoreState(state); else webView.loadUrl(HOME_URL);
    }
    private int dp(int x) { return (int)(x * getResources().getDisplayMetrics().density + 0.5f); }
    @Override protected void onSaveInstanceState(Bundle out) { super.onSaveInstanceState(out); webView.saveState(out); }
    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack(); else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        if (webView != null) { webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
