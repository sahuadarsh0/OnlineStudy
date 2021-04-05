package com.techipinfotech.onlinestudy1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techipinfotech.onlinestudy1.model.JSONResponse

class MainActivityViewModel : ViewModel() {

    var jsonResponse: MutableLiveData<List<JSONResponse?>?> =
        MutableLiveData<List<JSONResponse?>?>()

    fun setJsonResponse(jsonResponse: List<JSONResponse?>?) {
        this.jsonResponse.value = jsonResponse
    }
}