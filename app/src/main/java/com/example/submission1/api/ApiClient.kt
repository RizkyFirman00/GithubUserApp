package com.example.submission1.api

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val client = OkHttpClient.Builder()
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .apply {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            addInterceptor(loggingInterceptor)
        }
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val githubUserResponse = retrofit.create(ApiService::class.java)
}