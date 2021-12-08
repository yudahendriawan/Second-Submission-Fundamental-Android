package com.yudahendriawan.secondsubmissionfundamentalandroid.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: SearchItemUsers)

    @Delete
    fun delete(user: SearchItemUsers)

    @Query("SELECT * from searchitemusers ORDER BY login ASC")
    fun getAllFavoriteUsers(): LiveData<List<SearchItemUsers>>

}