package com.techipinfotech.onlinestudy1

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.techipinfotech.onlinestudy1.databinding.ActivityMainBinding
import com.techipinfotech.onlinestudy1.model.JSONResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.MD.alert
import technited.minds.androidutils.ProcessDialog
import technited.minds.androidutils.SharedPrefs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var processDialog: ProcessDialog
    private lateinit var navController: NavController
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var userSharedPreferences: SharedPrefs
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        processDialog = ProcessDialog(this)
        processDialog.show()

        userSharedPreferences = SharedPrefs(this, "USER")

        binding.apply {
            bottomNavigation.itemIconTintList = null
            bottomNavigation.background.alpha = 0
            bottomNavigation.setBackgroundResource(R.color.white)
            bottomNavigation.itemBackground = null
            bottomNavigation.selectedItemId = R.id.nav_home
        }

        navController = findNavController(R.id.nav_host_fragment)
        setBottomNavMenu(navController)

        getjsondata()

    }

    private fun getjsondata() {

        val getjsondata =
            HomeApi.getApiService().getjsondata(userSharedPreferences.get("student_mobile"))
        getjsondata.enqueue(object : Callback<List<JSONResponse?>?> {
            override fun onFailure(call: Call<List<JSONResponse?>?>, t: Throwable) {
                processDialog.dismiss()
                Log.d("asa", "onFailure: " + t.message)
            }

            override fun onResponse(
                call: Call<List<JSONResponse?>?>,
                response: Response<List<JSONResponse?>?>
            ) {
                val classes = response.body()
                processDialog.dismiss()
                viewModel.setJsonResponse(classes)
                if (classes?.get(0)?.notifications?.message != null
                    || classes?.get(0)?.notifications?.message != "null"
                    || classes?.get(0)?.notifications?.message != ""
                ) {
                    alert(
                        this@MainActivity,
                        classes?.get(0)?.notifications?.date,
                        classes?.get(0)?.notifications?.message,
                        "yes"
                    )
                }

            }
        })


    }

    private fun setBottomNavMenu(navController: NavController) {
        binding.bottomNavigation.let { NavigationUI.setupWithNavController(it, navController) }
    }

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination!!.id) {
            if (backPressedOnce) {
                super.onBackPressed()
            }
            backPressedOnce = true
            Toast.makeText(this, "Please Back again to exit", Toast.LENGTH_SHORT).show()

            val handler = Handler()
            handler.postDelayed({ backPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
    }
}