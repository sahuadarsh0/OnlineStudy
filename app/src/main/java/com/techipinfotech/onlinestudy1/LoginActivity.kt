package com.techipinfotech.onlinestudy1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.util.Log
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.techipinfotech.onlinestudy1.databinding.ActivityLoginBinding
import com.techipinfotech.onlinestudy1.model.Received
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.ProcessDialog
import technited.minds.androidutils.SharedPrefs


class LoginActivity : AppCompatActivity() {

    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var binding: ActivityLoginBinding
    private lateinit var processDialog: ProcessDialog
    private lateinit var i: Intent
    private lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        processDialog = ProcessDialog(this)
        userSharedPreferences = SharedPrefs(this, "USER")
        i = Intent(this, MainActivity::class.java)

        if (userSharedPreferences.get("student_id") != null) {
            startActivity(i)
            finish()
        }

        binding.apply {

            loginButton.setOnClickListener {
                if (username.text.toString() == "" || password.text.toString() == "") {
                    Toast.makeText(
                        this@LoginActivity,
                        "Username and Password mandatory",
                        Toast.LENGTH_SHORT
                    ).show()
                } else
                    login(username.text.toString(), password.text.toString())


            }
            animation = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.fade_in)
            triangle.startAnimation(animation)

        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return
        } else {
            val subscriptions =
                SubscriptionManager.from(applicationContext).activeSubscriptionInfoList
            for (i in subscriptions.indices) {
                val info = subscriptions[i]
                Log.d("asa", "number " + info.number)
                Log.d("asa", "network name : " + info.carrierName)
                Log.d("asa", "country iso " + info.countryIso)
            }
        }
        val subscriptions =
            SubscriptionManager.from(applicationContext).activeSubscriptionInfoList
        for (i in subscriptions.indices) {
            val info = subscriptions[i]
            Log.d("asa", "number " + info.number)
            Log.d("asa", "network name : " + info.carrierName)
            Log.d("asa", "country iso " + info.countryIso)
        }

    }

    private fun login(username: String, password: String) {
        processDialog.show()
        val getLogin = LoginApi.getApiService().login(username, password)
        getLogin.enqueue(object : Callback<Received> {
            override fun onFailure(call: Call<Received>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Login Error", Toast.LENGTH_SHORT).show()
                processDialog.dismiss()
            }

            override fun onResponse(call: Call<Received>, response: Response<Received>) {

                val received = response.body()
                processDialog.dismiss()

                if (received != null) {
                    if (received.status?.equals("Valid Login")!!) {
                        userSharedPreferences.set("student_id", received.studentData?.studentId)
                        userSharedPreferences.set("student_name", received.studentData?.studentName)
                        userSharedPreferences.set("class_id", received.studentData?.classId)
                        userSharedPreferences.set("class_name", received.studentData?.className)
                        userSharedPreferences.set(
                            "student_subject_id",
                            received.studentData?.studentSubjectId
                        )
                        userSharedPreferences.set(
                            "student_address",
                            received.studentData?.studentAddress
                        )
                        userSharedPreferences.set(
                            "student_mobile",
                            received.studentData?.studentMobile
                        )
                        userSharedPreferences.set(
                            "student_gender",
                            received.studentData?.studentGender
                        )
                        userSharedPreferences.set("student_dob", received.studentData?.studentDob)
                        userSharedPreferences.set(
                            "student_father",
                            received.studentData?.studentFather
                        )
                        userSharedPreferences.set(
                            "student_email",
                            received.studentData?.studentEmail
                        )
                        userSharedPreferences.set(
                            "admission_date",
                            received.studentData?.addmissionDate
                        )
                        userSharedPreferences.set(
                            "admission_last_date",
                            received.studentData?.admissionLastDate
                        )
                        userSharedPreferences.set("photo", received.studentData?.photo)
                        userSharedPreferences.set("total_fees", received.studentData?.totalFees)
                        userSharedPreferences.set("reference", received.studentData?.reference)
                        userSharedPreferences.set("remark", received.studentData?.remark)
                        userSharedPreferences.set(
                            "student_password",
                            binding.password.text.toString()
                        )
                        userSharedPreferences.apply()

                        startActivity(i)
                        finish()

                    }
                    Toast.makeText(this@LoginActivity, received.status, Toast.LENGTH_SHORT).show()

                }

            }
        })
    }
}
