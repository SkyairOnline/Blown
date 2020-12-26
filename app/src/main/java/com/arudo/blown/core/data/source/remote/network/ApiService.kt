package com.arudo.blown.core.data.source.remote.network

import com.arudo.blown.core.data.source.remote.response.GamesResponse
import com.arudo.blown.core.data.source.remote.response.ListGamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") key: String
    ): Response<ListGamesResponse>

    @GET("games/{id}")
    suspend fun getMovieData(
        @Path("id") id: Int,
        @Query("key") key: String
    ): Response<GamesResponse>
}