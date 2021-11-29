package com.yudahendriawan.secondsubmissionfundamentalandroid.api

import com.yudahendriawan.secondsubmissionfundamentalandroid.model.DetailUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.FollowUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.FollowUserResponseItem
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @Headers(
        "Authorization: token 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"
    )
    @GET("search/users")
    fun searchGithubUses(@Query("q") username: String): Call<SearchUserResponse>

    @Headers(
        "Authorization: token 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"
    )
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @Headers(
        "Authorization: token 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"
    )
    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<FollowUserResponseItem>>

    @Headers(
        "Authorization: token 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"
    )
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowUserResponseItem>>


}