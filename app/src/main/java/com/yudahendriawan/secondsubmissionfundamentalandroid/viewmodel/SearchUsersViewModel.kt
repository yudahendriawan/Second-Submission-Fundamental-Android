package com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.api.ApiConfig
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUsersViewModel: ViewModel() {

    private val _searchResults = MutableLiveData<List<SearchItemUsers>>()
    val searchResult: LiveData<List<SearchItemUsers>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private val TAG = SearchUsersViewModel::class.java.simpleName
    }

    fun searchGithubUsers(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchGithubUses(username)
        client.enqueue(object: Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _searchResults.value = response.body()?.items
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}", )
            }

        })
    }

}