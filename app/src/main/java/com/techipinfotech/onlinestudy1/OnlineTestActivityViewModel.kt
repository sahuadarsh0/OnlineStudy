package com.techipinfotech.onlinestudy1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techipinfotech.onlinestudy1.model.TestResponse

class OnlineTestActivityViewModel : ViewModel() {

    var testResponse: MutableLiveData<List<TestResponse?>?> =
        MutableLiveData<List<TestResponse?>?>()

    fun setTestResponse(testResponse: List<TestResponse?>?) {
        this.testResponse.value = testResponse
    }
    var testResultResponse: MutableLiveData<List<TestResponse?>?> =
        MutableLiveData<List<TestResponse?>?>()

    fun setTestResultResponse(testResultResponse: List<TestResponse?>?) {
        this.testResultResponse.value = testResultResponse
    }
}