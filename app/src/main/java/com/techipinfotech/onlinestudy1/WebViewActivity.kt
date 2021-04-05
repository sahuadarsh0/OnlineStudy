package com.techipinfotech.onlinestudy1

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.navigation.navArgs
import okhttp3.ResponseBody
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.SharedPrefs
import java.io.*


class WebViewActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var url: String
    private lateinit var fileName: String
    private lateinit var materialId: String
    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var webView: WebView
    private lateinit var buttons: Group
    private lateinit var view: Button
    private lateinit var download1: Button


    private val WRITE_EXST = 0x3


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
            if (hasStoragePermission()) {
//                download()
                buttons.visibility = View.GONE

                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
                Viewed.materialViewed(
                    this@WebViewActivity,
                    userSharedPreferences.get("student_mobile"),
                    materialId
                )

            } else {

                EasyPermissions.requestPermissions(
                    this,
                    "abc",
                    WRITE_EXST,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

            }
        }


    }

    private fun download() {

        Toast.makeText(this@WebViewActivity, "Downloading File", Toast.LENGTH_SHORT)
            .show()

        val downloadServices = HomeApi.getApiService().downloadPDF(url)
        downloadServices.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    Log.d("asa", "server contacted and has file")
                    val writtenToDisk: Boolean? =
                        response.body()?.let { writeResponseBodyToDisk(it) }
                    Log.d("asa", "file download was a success? $writtenToDisk")
                    Toast.makeText(this@WebViewActivity, "File Downloaded", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Log.d("asa", "File not Found")
                }

                finish()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


    }


    private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
        return try {
//            val iconFile = File(getExternalFilesDir(null).toString() + File.separator + fileName)


            val iconFile =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    File(Environment.getRootDirectory().absolutePath.toString() + "/" + fileName)
                } else {

                    File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + File.separator + fileName
                    )
                }
            Log.d("asa", "writeResponseBodyToDisk: $iconFile")


            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(iconFile)
                while (true) {
                    val read: Int = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d(
                        "asa",
                        "file download: $fileSizeDownloaded of $fileSize"
                    )
                }
                outputStream.flush()


                true

            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "Permission required to download file", Toast.LENGTH_SHORT).show()
    }


    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}