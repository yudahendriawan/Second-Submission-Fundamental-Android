package com.yudahendriawan.secondsubmissionfundamentalandroid.api

import com.yudahendriawan.secondsubmissionfundamentalandroid.model.DetailUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.FollowUserResponseItem
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.ReposResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    fun searchGithubUses(@Query("q") username: String): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<FollowUserResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowUserResponseItem>>

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") username: String) : Call<List<ReposResponse>>


}