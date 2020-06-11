package com.sist.lastapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        val link=intent.getStringExtra("link")

        news_webView.apply {
            settings.javaScriptEnabled=true
            webViewClient= WebViewClient()
        }
        news_webView.loadUrl(link)
    }
}