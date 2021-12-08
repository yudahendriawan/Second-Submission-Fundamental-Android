package com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import com.yudahendriawan.secondsubmissionfundamentalandroid.repository.UserRepository

class SavedFavoritesUserViewModel(application: Application) : ViewModel() {

    private val mUserRepository = UserRepository(application)

    fun insert(user: SearchItemUsers) = mUserRepository.insert(user)
    fun delete(user: SearchItemUsers) = mUserRepository.delete(user)

}