package com.techipinfotech.onlinestudy1;

import android.util.Log;

import com.techipinfotech.onlinestudy1.model.JSONResponse;
import com.techipinfotech.onlinestudy1.model.Received;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class LoginApi {

    public static final String base_url = API.LOGIN_URL.toString();


    public static ApiService apiService = null;

    public static ApiService getApiService() {


        if (apiService == null) {
            //create Ok HttP Client
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(s -> Log.d("ASA", s));
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);

//            create
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;

    }

    public interface ApiService {

//        @FormUrlEncoded
//        @POST("loginAuthentication")
//        Call<Received> login(
//                @Field("user_name") String user_name,
//                @Field("password") String password
//        );

        @GET("loginAuthentication/{user_name}/{password}")
        Call<Received> login(@Path("user_name") String user_name,
                             @Path("password") String password);

        @FormUrlEncoded
        @POST("changepassword")
        Call<Received> changePassword(
                @Field("user_name") String user_name,
                @Field("user_password") String password
        );

    }


}

