package com.example.newsapi;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebPageActivity extends AppCompatActivity {
    WebView webView;
    String webLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        webLink = intent.getStringExtra("link");

        webView.getSettings().setLoadsImagesAutomatically(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(webLink);

    }
}