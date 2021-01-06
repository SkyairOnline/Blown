package com.arudo.blown.core.data.source.remote.network

sealed class ApiResponse<out Q> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Error(val msg: String) : ApiResponse<Nothing>()
}