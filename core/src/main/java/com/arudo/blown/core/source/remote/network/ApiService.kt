package com.arudo.blown.core.source.remote.network

import com.arudo.blown.core.source.remote.response.DetailGamesResponse
import com.arudo.blown.core.source.remote.response.ListGamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games/lists/main")
    suspend fun getGames(
        @Query("key") key: String,
        @Query("discover") discover: Boolean,
        @Query("ordering") ordering: String,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
    ): ListGamesResponse

    @GET("games/{id}")
    suspend fun getDetailGames(
        @Path("id") id: Int,
        @Query("key") key: String
    ): Response<DetailGamesResponse>

    @GET("games")
    suspend fun getSearchGames(
        @Query("key") key: String,
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
    ): ListGamesResponse
}