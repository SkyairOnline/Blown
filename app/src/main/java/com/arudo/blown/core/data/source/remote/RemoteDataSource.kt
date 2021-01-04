package com.arudo.blown.core.data.source.remote

import com.arudo.blown.BuildConfig
import com.arudo.blown.core.data.source.remote.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher

class RemoteDataSource(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : BaseDataSource() {
    companion object {
        @Volatile
        private var remoteDataSource: RemoteDataSource? = null

        fun getInstance(apiService: ApiService, dispatcher: CoroutineDispatcher): RemoteDataSource =
            remoteDataSource ?: synchronized(this) {
                remoteDataSource ?: RemoteDataSource(apiService, dispatcher)
            }
    }

    private val key = BuildConfig.KEY

    suspend fun getGames() = getResult({ apiService.getGames(key) }, dispatcher)

    suspend fun getDetailGames(gamesId: Int) =
        getResult({ apiService.getDetailGames(gamesId, key) }, dispatcher)
}