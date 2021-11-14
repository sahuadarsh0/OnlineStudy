package com.techipinfotech.onlinestudy1.model


import com.google.gson.annotations.SerializedName

data class Demo(
    @SerializedName("demo_id")
    val demoId: String,
    @SerializedName("video_title")
    val videoTitle: String,
    @SerializedName("video_link")
    val videoLink: String
)