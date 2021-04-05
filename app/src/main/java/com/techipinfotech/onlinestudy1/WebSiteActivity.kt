package com.techipinfotech.onlinestudy1

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.navigation.navArgs
import technited.minds.androidutils.SharedPrefs


class WebSiteActivity : AppCompatActivity(){

    private lateinit var url: String
    private lateinit var fileName: String
    private lateinit var materialId: String
    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var webView: WebView
    private lateinit var buttons: Group


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webView)
        buttons = findViewById(R.id.buttons)
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)


        userSharedPreferences = SharedPrefs(this, "USER")
        val args: WebSiteActivityArgs by navArgs()
        fileName = args.url
        url = API.TEST_URL.toString() + fileName

        webView.loadUrl(url)

        webView.visibility = View.VISIBLE
        buttons.visibility = View.GONE


    }


}