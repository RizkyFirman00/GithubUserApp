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

class ViewModelMain : ViewModel() {

    val resultMainModel = MutableLiveData<ResultMain>()

    fun getDataUser() {
        viewModelScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubUserResponse
                        .getGithubMain()

                    emit(response)
                }.onStart {
                    resultMainModel.value = ResultMain.Loading(true)
                }.onCompletion {
                    resultMainModel.value = ResultMain.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    resultMainModel.value = ResultMain.Error(it)
                }.collect {
                    resultMainModel.value = ResultMain.Success(it)
                }
            }
        }
    }

    fun searchUserData(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubUserResponse
                        .searchGithubMain(
                            mapOf(
                                "q" to username,
                                "per_page" to 20
                            )
                        )

                    emit(response)
                }.onStart {
                    resultMainModel.value = ResultMain.Loading(true)
                }.onCompletion {
                    resultMainModel.value = ResultMain.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    resultMainModel.value = ResultMain.Error(it)
                }.collect {
                    resultMainModel.value = ResultMain.Success(it.items)
                }
            }
        }
    }
}