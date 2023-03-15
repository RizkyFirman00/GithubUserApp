package com.example.submission1.api

import com.example.submission1.BuildConfig
import com.example.submission1.model.GithubDetailResponse
import com.example.submission1.model.GithubUserResponse
import com.example.submission1.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //Fungsi nampilin user (Main)
    @GET("users")
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    fun getGithubMain(
    ): Call<GithubUserResponse.Item>

    //Fungsi Search (Main)
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    fun searchGithubMain(
        @Query("q") query: String
    ): Call<GithubUserResponse>

    //Fungsi nampilin user (Detail)
    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    fun getGithubDetail(
        @Path("username") username: String,
    ): Call<GithubDetailResponse>

    //Fungsi nampilin follower (Detail)
    @GET("users/{username}/followers")
    fun getGithubDetailFollower(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): Call<MutableList<GithubUserResponse.Item>>

    //Fungsi nampilin following (Detail)
    @GET("users/{username}/following")
    fun getGithubDetailFollowing(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): Call<MutableList<GithubUserResponse.Item>>


}