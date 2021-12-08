package com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import com.yudahendriawan.secondsubmissionfundamentalandroid.repository.UserRepository

class FavoritesViewModel(application: Application) : ViewModel() {

    private val mUserRepository = UserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<SearchItemUsers>> = mUserRepository.getAllFavoriteUsers()

}