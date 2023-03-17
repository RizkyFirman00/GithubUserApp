package com.example.submission1.localData

import android.content.Context
import androidx.room.Room

class DbModule(context: Context) {
    private val db = Room.databaseBuilder(context, DbApp::class.java, "usergithub.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}