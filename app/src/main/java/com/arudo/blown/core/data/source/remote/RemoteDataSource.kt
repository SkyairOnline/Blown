package com.arudo.blown.core.data.source.remote

import com.arudo.blown.BuildConfig
import com.arudo.blown.core.data.source.remote.network.ApiService

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {
    companion object {
        @Volatile
        private var remoteDataSource: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            remoteDataSource ?: synchronized(this) {
                remoteDataSource ?: RemoteDataSource(apiService)
            }
    }

    private val key = BuildConfig.KEY

    suspend fun getGames() = getResult {
        apiService.getGames(key)
    }

    suspend fun getDetailGames(gamesId: Int) = getResult {
        apiService.getDetailGames(gamesId, key)
    }
}