package com.arudo.blown.core.utils

import com.arudo.blown.core.utils.vo.Resource
import com.arudo.blown.core.utils.vo.Status
import kotlinx.coroutines.flow.*

fun <ResultType, RequestType> NetworkBoundResource(
    loadFromDatabase: () -> Flow<ResultType>,
    networkCall: suspend () -> Flow<Resource<out RequestType>>,
    saveCallResult: suspend (RequestType) -> Unit
): Flow<Resource<ResultType>> {
    return flow {
        emit(Resource.loading())
        val dataSource = loadFromDatabase().map {
            Resource.success(it)
        }
        emitAll(dataSource)
        val responseStatus = networkCall().first()
        if (responseStatus.status == Status.SUCCESS) {
            responseStatus.data?.let { saveCallResult(it) }
        } else if (responseStatus.status == Status.ERROR) {
            emit(Resource.error(responseStatus.message))
            emitAll(dataSource)
        }
    }
}