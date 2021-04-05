package com.techipinfotech.onlinestudy1;

import android.util.Log;

import com.techipinfotech.onlinestudy1.model.AnswersItem;
import com.techipinfotech.onlinestudy1.model.QuestionsItem;
import com.techipinfotech.onlinestudy1.model.Received;
import com.techipinfotech.onlinestudy1.model.TestResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class TestApi {

    public static final String base_url = API.NEW_TEST_URL.toString();


    private static ApiService apiService = null;

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

        @GET("gettest/{mobile_no}")
        Call<List<TestResponse>> gettest(@Path("mobile_no") String mobile_no);

        @GET("gettestresult/{mobile_no}")
        Call<List<TestResponse>> gettestresult(@Path("mobile_no") String mobile_no);

        @Headers("Content-Type: application/json")
        @POST("submittest/")
        Call<Received> submittest(@Body List<AnswersItem> answersItem);

        @FormUrlEncoded
        @POST("getquestion")
        Call<List<QuestionsItem>> getQuestion(
                @Field("exam_id") String exam_id
        );


    }


}

