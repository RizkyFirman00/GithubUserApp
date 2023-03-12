package com.example.submission1.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.ResultMain
import com.example.submission1.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ViewModelDetail : ViewModel() {
    val resultDetailModel = MutableLiveData<ResultMain>()
    val resultDetailFollowerModel = MutableLiveData<ResultMain>()
    val resultDetailFollowingModel = MutableLiveData<ResultMain>()

    fun getDataDetailUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubUserResponse
                        .getGithubDetail(username)

                    emit(response)
                }.onStart {
                    resultDetailModel.value = ResultMain.Loading(true)
                }.onCompletion {
                    resultDetailModel.value = ResultMain.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    resultDetailModel.value = ResultMain.Error(it)
                }.collect {
                    resultDetailModel.value = ResultMain.Success(it)
                }
            }
        }
    }

    fun getDataDetailFollower(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubUserResponse
                        .getGithubDetailFollower(username)

                    emit(response)
                }.onStart {
                    resultDetailFollowerModel.value = ResultMain.Loading(true)
                }.onCompletion {
                    resultDetailFollowerModel.value = ResultMain.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    resultDetailFollowerModel.value = ResultMain.Error(it)
                }.collect {
                    resultDetailFollowerModel.value = ResultMain.Success(it)
                }
            }
        }
    }

    fun getDataDetailFollowing(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubUserResponse
                        .getGithubDetailFollowing(username)

                    emit(response)
                }.onStart {
                    resultDetailFollowingModel.value = ResultMain.Loading(true)
                }.onCompletion {
                    resultDetailFollowingModel.value = ResultMain.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    resultDetailFollowingModel.value = ResultMain.Error(it)
                }.collect {
                    resultDetailFollowingModel.value = ResultMain.Success(it)
                }
            }
        }
    }
}