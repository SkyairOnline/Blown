package com.arudo.blown.core.data.source.remote

import com.arudo.blown.core.utils.vo.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val responseCall = call()
            if (responseCall.isSuccessful) {
                val responseBody = responseCall.body()
                if (responseBody != null) {
                    return Resource.success(responseBody)
                }
            }
            return Resource.error("${responseCall.code()} ${responseCall.message()}", null)
        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString(), null)
        }
    }
}