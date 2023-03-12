package com.example.submission1.api

import com.example.submission1.BuildConfig
import com.example.submission1.model.GithubDetailResponse
import com.example.submission1.model.GithubUserResponse
import retrofit2.http.*

interface ApiService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getGithubMain(
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<GithubUserResponse.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getGithubDetail(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): GithubDetailResponse

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getGithubDetailFollower(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<GithubUserResponse.Item>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getGithubDetailFollowing(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<GithubUserResponse.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchGithubMain(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): GithubUserResponse
}