package com.example.submission1.localData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submission1.model.GithubUserResponse

@Database(entities = [GithubUserResponse.Item::class], version = 1, exportSchema = false)
abstract class DbApp:RoomDatabase() {
    abstract fun userDao(): UserDao
}