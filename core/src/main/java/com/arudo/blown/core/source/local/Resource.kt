package com.arudo.blown.core.source.local

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(msg: String, data: T? = null) : Resource<T>(data, msg)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}