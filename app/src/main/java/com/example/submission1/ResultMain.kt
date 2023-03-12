package com.example.submission1

sealed class ResultMain {
    data class Success<out T>(val data: T) : ResultMain()
    data class Error(val exception: Throwable) : ResultMain()
    data class Loading(val isLoading: Boolean) : ResultMain()
}
