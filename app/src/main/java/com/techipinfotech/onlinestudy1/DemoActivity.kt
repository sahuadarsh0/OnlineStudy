package com.techipinfotech.onlinestudy1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.techipinfotech.onlinestudy1.adapter.DemoAdapter
import com.techipinfotech.onlinestudy1.databinding.ActivityDemoBinding
import com.techipinfotech.onlinestudy1.model.Demo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding
    private val demoAdapter = DemoAdapter(this::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo)
        getDemoData()
        binding.contents.adapter = demoAdapter
    }

    private fun getDemoData() {
        val getDemoData = DemoApi.getApiService().demoData
        getDemoData.enqueue(object : Callback<List<Demo>> {
            override fun onResponse(call: Call<List<Demo>>, response: Response<List<Demo>>) {
                demoAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Demo>>, t: Throwable) {
            }
        })
    }

    private fun onItemClicked(demo: Demo) {
        val intent = Intent(this, PlayDemo::class.java)
        intent.putExtra("url", demo.videoLink)
        startActivity(intent)
    }
}