package com.techipinfotech.onlinestudy1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.net.toUri
import androidx.navigation.navArgs
import com.techipinfotech.onlinestudy1.utils.SharedPrefs


class WebViewActivity : AppCompatActivity(){

    private lateinit var url: String
    private lateinit var fileName: String
    private lateinit var materialId: String
    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var webView: WebView
    private lateinit var buttons: Group
    private lateinit var view: Button
    private lateinit var download1: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webView)
        buttons = findViewById(R.id.buttons)
        view = findViewById(R.id.view)
        download1 = findViewById(R.id.download)
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true


        userSharedPreferences = SharedPrefs(this, "USER")
        val args: WebViewActivityArgs by navArgs()
        fileName = args.url
        url = API.PDF.toString() + fileName
        materialId = args.materialId


        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")

        webView.visibility = View.GONE

        view.setOnClickListener {
            webView.visibility = View.VISIBLE
            buttons.visibility = View.GONE


            Viewed.materialViewed(
                this@WebViewActivity,
                userSharedPreferences.get("student_mobile"),
                materialId
            )
        }

        download1.setOnClickListener {
                buttons.visibility = View.GONE

                val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())
                startActivity(browserIntent)
                Viewed.materialViewed(
                    this@WebViewActivity,
                    userSharedPreferences.get("student_mobile"),
                    materialId
                )

        }


    }



}