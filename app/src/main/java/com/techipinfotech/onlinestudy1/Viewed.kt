package com.techipinfotech.onlinestudy1

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.ProcessDialog
import com.techipinfotech.onlinestudy1.model.Received

object Viewed {
    private lateinit var processDialog: ProcessDialog

    @JvmStatic
    fun materialViewed(context: Context, username: String, material_id: String) {
        processDialog = ProcessDialog(context)
        processDialog.show()

        val updateViewStatus = HomeApi.getApiService().updateViewedStatus(username, material_id)
        updateViewStatus.enqueue(object : Callback<Received> {
            override fun onFailure(call: Call<Received>, t: Throwable) {
                processDialog.dismiss()
                Log.d("asa", "view Status NOT updated")

            }

            override fun onResponse(call: Call<Received>, response: Response<Received>) {
                processDialog.dismiss()
                Log.d("asa", "view Status updated")

            }
        })
    }
}