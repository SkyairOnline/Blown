package com.arudo.blown.core.utils

import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

fun <ResultType, RequestType> NetworkBoundResource(
    loadFromDatabase: () -> Flow<ResultType>,
    networkCall: suspend () -> Flow<ApiResponse<RequestType>>,
    saveCallResult: suspend (RequestType) -> Unit
): Flow<Resource<ResultType>> {
    return flow {
        emit(Resource.Loading())
        when (val responseStatus = networkCall().first()) {
            is ApiResponse.Success -> {
                responseStatus.data?.let { saveCallResult(it) }
                emitAll(loadFromDatabase().map {
                    Resource.Success(it)
                })
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<ResultType>(responseStatus.msg))
                val dataSource = loadFromDatabase().first()
                if (dataSource != null) {
                    emitAll(loadFromDatabase().map {
                        Resource.Success(it)
                    })
                }
            }
        }
    }
}