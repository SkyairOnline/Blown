package com.arudo.blown.core.source.remote

import com.arudo.blown.core.source.remote.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher

class RemoteDataSource(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher,
    private val key: String
) : BaseDataSource() {

    suspend fun getGames(page: Int, pageSize: Int) =
        apiService.getGames(key, true, "-added", page, pageSize)

    suspend fun getDetailGames(gamesId: Int) =
        getResult({ apiService.getDetailGames(gamesId, key) }, dispatcher)

    suspend fun getSearchGames(search: String, page: Int, pageSize: Int) =
        apiService.getSearchGames(key, search, page, pageSize)
}