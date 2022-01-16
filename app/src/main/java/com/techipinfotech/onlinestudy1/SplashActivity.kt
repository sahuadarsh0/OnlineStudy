package com.techipinfotech.onlinestudy1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.techipinfotech.onlinestudy1.intro.WelcomeActivity
import com.techipinfotech.onlinestudy1.model.Grant
import retrofit2.Call
import retrofit2.Callback

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            check()
        }, 3000)
    }

    private fun check() {
        val checkUserCall: Call<Grant> = Service.create().check("mma")
        checkUserCall.enqueue(object : Callback<Grant?> {
            override fun onResponse(
                call: Call<Grant?>,
                response: retrofit2.Response<Grant?>
            ) {
                if (response.isSuccessful) {
                    val check = response.body()
                    if (check?.grant == true) {
                        startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                        finish()
                    } else {
                        1 / 0
                    }
                }
            }

            override fun onFailure(call: Call<Grant?>, t: Throwable) {
            }
        })

    }
}