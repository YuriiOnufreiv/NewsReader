package com.example.newsreader.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.myapplication.R;

public class ArticleDetailsActivity extends Activity {

    WebView webView;
    ProgressBar progressBar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.article_details_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        webView = (WebView) findViewById(R.id.detailsWebView);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new CustomWebClient());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(intent.getStringExtra("SOURCE"));

        webView.loadUrl(url);
    }

    public class CustomWebClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
