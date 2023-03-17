package com.example.submission1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission1.localData.DbModule
import com.example.submission1.model.GithubUserResponse

class ViewModelFavorite(private val dbModule: DbModule) : ViewModel() {

    fun getUserFavorite(): LiveData<MutableList<GithubUserResponse.Item>>{
        return dbModule.userDao.loadAll()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewModelFavorite(db) as T
    }

}