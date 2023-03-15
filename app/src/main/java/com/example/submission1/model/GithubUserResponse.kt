package com.example.submission1.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class GithubUserResponse(
    val items: MutableList<Item>
)

{
    @Parcelize
    @Entity(tableName = "user")
    data class Item(
        @ColumnInfo(name = "avatar_url")
        val avatar_url: String,
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "login")
        val login: String
    ) : Parcelable
}