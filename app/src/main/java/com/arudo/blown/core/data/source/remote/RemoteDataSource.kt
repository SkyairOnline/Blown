package com.arudo.blown.core.data.source.remote

import com.arudo.blown.BuildConfig
import com.arudo.blown.core.data.source.remote.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher

class RemoteDataSource(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : BaseDataSource() {

    private val key = BuildConfig.KEY

    suspend fun getGames() = getResult({ apiService.getGames(key) }, dispatcher)

    suspend fun getDetailGames(gamesId: Int) =
        getResult({ apiService.getDetailGames(gamesId, key) }, dispatcher)

    suspend fun getSearchGames(search: String) =
        getResult({ apiService.getSearchGames(key, search, true) }, dispatcher)
}