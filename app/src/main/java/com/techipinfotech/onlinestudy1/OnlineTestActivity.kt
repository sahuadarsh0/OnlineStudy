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
import com.rezwan.knetworklib.KNetwork
import com.techipinfotech.onlinestudy1.databinding.ActivityOnlineTestBinding
import com.techipinfotech.onlinestudy1.model.TestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.ProcessDialog
import technited.minds.androidutils.SharedPrefs

class OnlineTestActivity : AppCompatActivity(), KNetwork.OnNetWorkConnectivityListener {

    private lateinit var binding: ActivityOnlineTestBinding
    private lateinit var processDialog: ProcessDialog
    private lateinit var navController: NavController
    lateinit var viewModel: OnlineTestActivityViewModel
    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var testSharedPreferences: SharedPrefs
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_test)
        viewModel = ViewModelProvider(this).get(OnlineTestActivityViewModel::class.java)

        processDialog = ProcessDialog(this)
        KNetwork.bind(this, lifecycle)
            .showKNDialog(false)
            .setConnectivityListener(this)

        userSharedPreferences = SharedPrefs(this, "USER")
        testSharedPreferences = SharedPrefs(this, "TEST")

        binding.apply {
            bottomNavigation.itemIconTintList = null
            bottomNavigation.background.alpha = 0
            bottomNavigation.setBackgroundResource(R.color.white)
            bottomNavigation.itemBackground = null
            bottomNavigation.selectedItemId = R.id.nav_info
        }

        navController = findNavController(R.id.test_nav_host_fragment)
        setBottomNavMenu(navController)

        getTest();
    }

    private fun setBottomNavMenu(navController: NavController) {
        binding.bottomNavigation.let { NavigationUI.setupWithNavController(it, navController) }
    }

    override fun onNetConnected() {

    }

    override fun onNetDisConnected() {
        Toast.makeText(this, "Internet Disconnected Exam Over", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onNetError(msg: String?) {

    }

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination!!.id) {
            if (backPressedOnce) {
                super.onBackPressed()
            }
            backPressedOnce = true
            Toast.makeText(this, "Press back again to exit Exam Section", Toast.LENGTH_SHORT).show()
            val handler = Handler()
            handler.postDelayed({ backPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
    }

    private fun getTest() {

        processDialog.show()
        val getTest =
            TestApi.getApiService().gettest(userSharedPreferences.get("student_mobile"))
        getTest.enqueue(object : Callback<List<TestResponse?>?> {
            override fun onFailure(call: Call<List<TestResponse?>?>, t: Throwable) {
                processDialog.dismiss()
                Log.d("asa", "onFailure: " + t.message)
            }

            override fun onResponse(
                call: Call<List<TestResponse?>?>,
                response: Response<List<TestResponse?>?>
            ) {
                val test = response.body()
                processDialog.dismiss()
                viewModel.setTestResponse(test)


            }
        })

        val getTestResult =
            TestApi.getApiService().gettestresult(userSharedPreferences.get("student_mobile"))
        getTestResult.enqueue(object : Callback<List<TestResponse?>?> {
            override fun onFailure(call: Call<List<TestResponse?>?>, t: Throwable) {
                processDialog.dismiss()
                Log.d("asa", "onFailure: " + t.message)
            }

            override fun onResponse(
                call: Call<List<TestResponse?>?>,
                response: Response<List<TestResponse?>?>
            ) {
                val result = response.body()
                processDialog.dismiss()
                viewModel.setTestResultResponse(result)


            }
        })


    }

}