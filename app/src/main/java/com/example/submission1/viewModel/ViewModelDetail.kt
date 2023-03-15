package com.example.submission1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.submission1.ResultMain
import com.example.submission1.api.ApiClient
import com.example.submission1.localData.DbModule
import com.example.submission1.model.GithubDetailResponse
import com.example.submission1.model.GithubUserResponse
import com.example.submission1.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelDetail(private val db: DbModule) : ViewModel() {
    val resultDetailModel = MutableLiveData<GithubDetailResponse>()
    val resultDetailFollowerModel = MutableLiveData<MutableList<GithubUserResponse.Item>>()
    val resultDetailFollowingModel = MutableLiveData<MutableList<GithubUserResponse.Item>>()
    val resultSuccessFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false
    fun setFavorite(item: GithubUserResponse.Item?) {
        viewModelScope.launch {
            item?.let {
                if (isFavorite) {
                    db.userDao.delete(item)
                    resultDeleteFavorite.value = true
                } else {
                    db.userDao.insert(item)
                    resultSuccessFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null) {
                listenFavorite()
                isFavorite = true
            }
        }
    }

    fun setDataDetailUser(username: String) {
        ApiClient.githubUserResponse
            .getGithubDetail(username)
            .enqueue(object : Callback<GithubDetailResponse> {
                override fun onResponse(
                    call: Call<GithubDetailResponse>,
                    response: Response<GithubDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        resultDetailModel.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<GithubDetailResponse>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
//        viewModelScope.launch(Dispatchers.IO) {
//            launch(Dispatchers.Main) {
//                flow {
//                    val response = ApiClient
//                        .githubUserResponse
//                        .getGithubDetail(username)
//
//                    emit(response)
//                }.onStart {
//                    resultDetailModel.value = ResultMain.Loading(true)
//                }.onCompletion {
//                    resultDetailModel.value = ResultMain.Loading(false)
//                }.catch {
//                    Log.e("Error", it.message.toString())
//                    resultDetailModel.value = ResultMain.Error(it)
//                }.collect {
//                    resultDetailModel.value = ResultMain.Success(it)
//                }
//            }
//        }
    }

    fun getDataDetailUser() :LiveData<GithubDetailResponse>{
        return resultDetailModel
    }

    fun setDataDetailFollower(username: String) {
        ApiClient.githubUserResponse
            .getGithubDetailFollower(username)
            .enqueue(object : Callback<MutableList<GithubUserResponse.Item>> {
                override fun onResponse(
                    call: Call<MutableList<GithubUserResponse.Item>>,
                    response: Response<MutableList<GithubUserResponse.Item>>
                ) {
                    if (response.isSuccessful) {
                        resultDetailFollowerModel.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<GithubUserResponse.Item>>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
//        viewModelScope.launch(Dispatchers.IO) {
//            launch(Dispatchers.Main) {
//                flow {
//                    val response = ApiClient
//                        .githubUserResponse
//                        .getGithubDetailFollower(username)
//
//                    emit(response)
//                }.onStart {
//                    resultDetailFollowerModel.value = ResultMain.Loading(true)
//                }.onCompletion {
//                    resultDetailFollowerModel.value = ResultMain.Loading(false)
//                }.catch {
//                    Log.e("Error", it.message.toString())
//                    resultDetailFollowerModel.value = ResultMain.Error(it)
//                }.collect {
//                    resultDetailFollowerModel.value = ResultMain.Success(it)
//                }
//            }
//        }
    }
    fun getDataDetailFollower() :LiveData<MutableList<GithubUserResponse.Item>>{
        return resultDetailFollowerModel
    }

    fun setDataDetailFollowing(username: String) {
        ApiClient.githubUserResponse
            .getGithubDetailFollowing(username)
            .enqueue(object : Callback<MutableList<GithubUserResponse.Item>> {
                override fun onResponse(
                    call: Call<MutableList<GithubUserResponse.Item>>,
                    response: Response<MutableList<GithubUserResponse.Item>>
                ) {
                    if (response.isSuccessful) {
                        resultDetailFollowingModel.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<GithubUserResponse.Item>>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
    }
    fun getDataDetailFollowing() :LiveData<MutableList<GithubUserResponse.Item>>{
        return resultDetailFollowingModel
    }

    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewModelDetail(db) as T
    }
}
