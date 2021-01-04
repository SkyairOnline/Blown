package com.arudo.blown.core.data.source.remote

import com.arudo.blown.core.utils.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseDataSource {
    suspend fun <T> getResult(
        call: suspend () -> Response<T>,
        dispatcher: CoroutineDispatcher
    ): Flow<Resource<out T>> {
        return flow {
            try {
                val responseCall = call()
                if (responseCall.isSuccessful) {
                    val responseBody = responseCall.body()
                    if (responseBody != null) {
                        emit(Resource.success(responseBody))
                    }
                }
                emit(Resource.error("${responseCall.code()} ${responseCall.message()}", null))
            } catch (e: Exception) {
                emit(Resource.error(e.message ?: e.toString(), null))
            }
        }.flowOn(dispatcher)
    }
}