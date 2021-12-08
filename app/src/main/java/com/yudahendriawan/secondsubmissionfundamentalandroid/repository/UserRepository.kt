package com.yudahendriawan.secondsubmissionfundamentalandroid.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.yudahendriawan.secondsubmissionfundamentalandroid.database.UserDao
import com.yudahendriawan.secondsubmissionfundamentalandroid.database.UserDatabase
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {

    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<SearchItemUsers>> = mUserDao.getAllFavoriteUsers()

    fun insert(user: SearchItemUsers) {
        executorService.execute {
            mUserDao.insert(user)
        }
    }

    fun delete(user: SearchItemUsers) {
        executorService.execute {
            mUserDao.delete(user)
        }
    }

}