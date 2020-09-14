package com.example.news.view.webView

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.news.R

class WebViewActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    lateinit var toolbar: Toolbar

    lateinit var webChromeClient: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initView()
        onClickListener()
        loadWebView()
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progressBarWeb)
        webChromeClient = findViewById(R.id.wvArticles)
        (findViewById(R.id.textViewTitleAppBar) as TextView).text = intent.getStringExtra("name")
    }

    private fun onClickListener() {
        toolbar.setNavigationOnClickListener { v: View? -> finish() }
    }

    private fun loadWebView() {
        webChromeClient.settings.loadsImagesAutomatically = true
        webChromeClient.settings.javaScriptEnabled = true
        webChromeClient.settings.domStorageEnabled = true
        webChromeClient.settings.setSupportZoom(false)
        webChromeClient.settings.builtInZoomControls = false
        webChromeClient.settings.displayZoomControls = false
        webChromeClient.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webChromeClient.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                val animation =
                    ObjectAnimator.ofInt(progressBar, "progress", newProgress)
                animation.duration = 1400
                animation.interpolator = AccelerateDecelerateInterpolator()
                animation.start()
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                    animation.cancel()
                }
            }
        }
        webChromeClient.loadUrl(intent.getStringExtra("url")!!)
    }
}