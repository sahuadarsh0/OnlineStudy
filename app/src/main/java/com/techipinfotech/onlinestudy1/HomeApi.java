package com.techipinfotech.onlinestudy1;

import android.util.Log;

import java.util.List;

import com.techipinfotech.onlinestudy1.model.JSONResponse;
import com.techipinfotech.onlinestudy1.model.Received;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

class HomeApi {

    public static final String base_url = API.HOME_URL.toString();

    private static ApiService apiService = null;

    static ApiService getApiService() {


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

        @GET("getjsondata/{mobile_no}")
        Call<List<JSONResponse>> getjsondata(@Path("mobile_no") String mobile_no);

        @GET("updatevideoviewedstatus/{username}/{material_id}")
        Call<Received> updateViewedStatus(
                @Path("username") String username,
                @Path("material_id") String material_id
        );

        @Streaming
        @GET
        Call<ResponseBody> downloadPDF(@Url String fileUrl);


    }

}

