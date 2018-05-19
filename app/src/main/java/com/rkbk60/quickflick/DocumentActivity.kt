package com.rkbk60.quickflick

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class DocumentActivity : AppCompatActivity() {

    private var webView: WebView? = null

    private val webClientForLollipop = object : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url?.toString())
            return true
        }
    }

    private val webClientForKitkat = object : WebViewClient() {
        @Suppress("OverridingDeprecatedMember")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document)

        webView = findViewById<WebView>(R.id.document_view)?.apply {
            setTitle("QuickFlick Document")
            settings.builtInZoomControls = true
            webViewClient = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webClientForLollipop
            } else {
                webClientForKitkat
            }
            loadUrl("file:///android_asset/Home.html")
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        webView?.let {
            if (keyCode == KeyEvent.KEYCODE_BACK && it.canGoBack()) {
                it.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
