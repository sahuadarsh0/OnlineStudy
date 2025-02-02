package com.techipinfotech.onlinestudy1

import android.util.Log
import com.techipinfotech.onlinestudy1.model.ChaptersItem
import com.techipinfotech.onlinestudy1.model.ContentItem
import com.techipinfotech.onlinestudy1.model.JSONResponse
import com.techipinfotech.onlinestudy1.model.Received
import com.techipinfotech.onlinestudy1.model.TopicsItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

object HomeApi {

    private val BASE_URL = API.HOME_URL.toString()

    private var apiService: ApiService? = null

    fun getApiService(): ApiService {
        if (apiService == null) {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Log.d("ASA", message) }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService!!
    }

    interface ApiService {

        @GET("getjsondata/{mobile_no}")
        fun getJsonData(@Path("mobile_no") mobileNo: String): Call<List<JSONResponse>>

//        @GET("getsubject_jsondata/{student_id}/{subject_id}")
//        fun getSubjectJsonData(
//            @Path("student_id") studentId: String,
//            @Path("subject_id") subjectId: String
//        ): Call<List<ChaptersItem>>

        @GET("getchapter/{subject_id}/{student_id}")
        fun getChapterJsonData(
            @Path("subject_id") subjectId: String,
            @Path("student_id") studentId: String
        ): Call<List<ChaptersItem>>

        @GET("gettopic/{subject_id}/{chapter_id}/{student_id}")
        fun getTopicJsonData(
            @Path("subject_id") subjectId: String,
            @Path("chapter_id") chapterId: String,
            @Path("student_id") studentId: String
        ): Call<List<TopicsItem>>

        @GET("getvideo/{subject_id}/{chapter_id}/{topic_id}/{student_id}")
        fun getVideo(
            @Path("subject_id") subjectId: String,
            @Path("chapter_id") chapterId: String,
            @Path("topic_id") topicId: String,
            @Path("student_id") studentId: String,
        ): Call<List<ContentItem>>

        @GET("updatevideoviewedstatus/{username}/{material_id}")
        fun updateViewedStatus(
            @Path("username") username: String,
            @Path("material_id") materialId: String
        ): Call<Received>

    }
}
