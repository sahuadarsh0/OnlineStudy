package com.techipinfotech.onlinestudy1

import android.util.Log
import com.techipinfotech.onlinestudy1.model.Grant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {


    @GET("permissions/{website}/")
    fun check(
        @Path("website")
        website: String?
    ): Call<Grant>

    companion object {

        private var BASE_URL = "http://technitedminds.com/admin/"

        fun create(): Service {
            val builder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor { s: String? -> Log.d("ASA", s!!) }

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(Service::class.java)
        }
    }
}
