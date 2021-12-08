package com.yudahendriawan.secondsubmissionfundamentalandroid.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchUserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<SearchItemUsers>
) : Parcelable

@Entity
@Parcelize
data class SearchItemUsers(

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,

    @field:SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @field:SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String
) : Parcelable
