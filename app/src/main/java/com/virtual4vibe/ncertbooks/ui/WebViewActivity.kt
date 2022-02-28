package com.virtual4vibe.ncertbooks.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.github.ybq.android.spinkit.SpinKitView
import com.virtual4vibe.ncertbooks.ads.UnityInterStitialAds
import com.virtual4vibe.ncertbooks.databinding.ActivityWebViewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder

@AndroidEntryPoint
class WebViewActivity : BaseActivity() {
    lateinit var bind: ActivityWebViewBinding

    init {
        UnityInterStitialAds.loadAd("webViewbackPressAds")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val pdfLink = intent.getStringExtra("downloadLink").toString()
        Log.v("link", "$pdfLink")
        bind.spinWebView.bringToFront()
        window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        openPdf(pdfLink, bind.spinWebView)

        bind.reload.setOnClickListener {
            openPdf(pdfLink, bind.spinWebView)
        }
    }

    private fun openPdf(link: String, spin: SpinKitView) {
        spin.visibility = View.VISIBLE
        bind.webView.settings.javaScriptEnabled = true

        Log.v("link after parsing", "$link")
//        "https://drive.google.com/viewerng/viewer?embedded=true&url="

//        "https://docs.google.com/gview?embedded=true&url="
        bind.webView.loadUrl(
            "https://drive.google.com/viewerng/viewer?embedded=true&url=" + URLEncoder.encode(
                link,
                "ISO-8859-1"
            )
        )
        bind.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                spin.visibility = View.GONE
            }
        }

    }

    private fun destroyWebView() {
        bind.webView.removeAllViews()
        bind.webView.clearHistory()
        bind.webView.clearCache(true)
        bind.webView.loadUrl("")
        bind.webView.onPause()
        bind.webView.removeAllViews()
        bind.webView.destroyDrawingCache()
        bind.webView.destroy()
    }

    override fun finish() {
        super.finish()
        destroyWebView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        UnityInterStitialAds.showAd("webViewbackPressAds" , this)
        finish()
    }
}