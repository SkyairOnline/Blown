package com.arudo.blown.core.domain.repository

import androidx.paging.PagedList
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.source.local.Resource
import kotlinx.coroutines.flow.Flow

interface IBlownRepository {
    fun getGames(): Flow<Resource<List<Games>>>
    fun getDetailGame(gamesId: Int): Flow<Resource<Games>>
    fun getSearchGames(search: String): Flow<Resource<List<Games>>>
    fun getListGamesFavorites(): Flow<PagedList<Games>>
    fun getGamesFavorite(favoriteGamesId: Int): Flow<FavoriteGames?>
    suspend fun insertFavoriteGame(favoriteGamesId: Int)
    suspend fun deleteFavoriteGame(favoriteGamesId: Int)
}