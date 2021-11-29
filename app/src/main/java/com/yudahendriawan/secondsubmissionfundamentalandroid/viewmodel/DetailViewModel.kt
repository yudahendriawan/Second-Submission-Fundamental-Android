package com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.api.ApiConfig
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.DetailUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.FollowUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.FollowUserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _userFollowers = MutableLiveData<List<FollowUserResponseItem>>()
    val userFollowers: LiveData<List<FollowUserResponseItem>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<FollowUserResponseItem>>()
    val userFollowing: LiveData<List<FollowUserResponseItem>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        private val TAG = DetailViewModel::class.java.simpleName
    }

    fun getDetailUser(username: String){
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if(response.isSuccessful){
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}", )
            }

        })
    }

    fun getUserFollowers(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object: Callback<List<FollowUserResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowUserResponseItem>>,
                response: Response<List<FollowUserResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _userFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<List<FollowUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}", )
            }

        })
    }

    fun getUserFollowing(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object: Callback<List<FollowUserResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowUserResponseItem>>,
                response: Response<List<FollowUserResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _userFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<List<FollowUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}", )
            }

        })
    }

}