package com.lolblach333.tutu.data

import com.lolblach333.tutu.BuildConfig
import com.lolblach333.tutu.data.news.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>

    companion object {
        var BASE_URL = "https://newsapi.org/v2/"

        fun create(): ApiInterface {
            val okHttpClientBuilder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }.let(okHttpClientBuilder::addInterceptor)
            }

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)
        }
    }
}
