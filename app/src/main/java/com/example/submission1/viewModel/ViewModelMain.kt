package com.example.submission1.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.submission1.api.ApiClient
import com.example.submission1.localData.SettingPreferences
import com.example.submission1.model.GithubUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelMain(private val preferences: SettingPreferences) : ViewModel() {

    val resultGetMainModel = MutableLiveData<GithubUserResponse.Item>()
    val resultMainModel = MutableLiveData<MutableList<GithubUserResponse.Item>>()

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun setDataUser() {
        ApiClient.githubUserResponse
            .getGithubMain()
            .enqueue(object : Callback<GithubUserResponse.Item> {
                override fun onResponse(
                    call: Call<GithubUserResponse.Item>,
                    response: Response<GithubUserResponse.Item>
                ) {
                    if (response.isSuccessful) {
                        resultGetMainModel.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<GithubUserResponse.Item>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
    }

    fun getDataUser(): LiveData<MutableList<GithubUserResponse.Item>> {
        return resultMainModel
    }

    fun searchUserData(username: String) {
        ApiClient.githubUserResponse
            .searchGithubMain(username)
            .enqueue(object : Callback<GithubUserResponse> {
                override fun onResponse(
                    call: Call<GithubUserResponse>,
                    response: Response<GithubUserResponse>
                ) {
                    if (response.isSuccessful) {
                        resultMainModel.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
    }

    fun getSearchDataUser(): LiveData<MutableList<GithubUserResponse.Item>> {
        return resultMainModel
    }

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ViewModelMain(preferences) as T
    }
}