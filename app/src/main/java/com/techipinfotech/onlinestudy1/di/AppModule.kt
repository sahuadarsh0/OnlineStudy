package com.techipinfotech.onlinestudy1.di

import android.content.Context
import android.util.Log
import com.techipinfotech.onlinestudy1.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import technited.minds.androidutils.SharedPrefs
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutine() = Dispatchers.IO

//    @Provides
//    @Singleton
//    fun provideBaseUrl() = Constants.BASE_URL.toString()

    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor { s: String? -> Log.d("asa", s!!) }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
//
//
//    @Singleton
//    @Provides
//    fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)
//
//
//    @Singleton
//    @Provides
//    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)


    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext appContext: Context) = SharedPrefs(appContext, "USER")

//    @Singleton
//    @Provides
//    fun provideQuestionsDao(db : AppDatabase) = db.questionsDao()
//
//
//    @Singleton
//    @Provides
//    fun provideAnswersDao(db : AppDatabase) = db.answersDao()
//
//    @Singleton
//    @Provides
//    fun provideBlogsDao(db : AppDatabase) = db.blogsDao()
//
//    @Singleton
//    @Provides
//    fun provideGalleryDao(db : AppDatabase) = db.galleryDao()

//    @Singleton
//    @Provides
//    fun provideRepository(
//        remoteDataSource: RemoteDataSource,
//        localGalleryDataSource: GalleryDao,
//        localBlogsDataSource: BlogsDao
//    ) = MainRepository(remoteDataSource,localGalleryDataSource, localBlogsDataSource)
}