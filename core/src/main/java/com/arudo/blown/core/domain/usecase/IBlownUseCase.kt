package com.arudo.blown.core.domain.usecase

import androidx.paging.PagingData
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.source.local.Resource
import kotlinx.coroutines.flow.Flow

interface IBlownUseCase {
    fun getGames(): Flow<PagingData<Games>>
    fun getDetailGame(gamesId: Int): Flow<Resource<Games>>
    fun getSearchGames(search: String): Flow<PagingData<Games>>
    fun getListGamesFavorites(): Flow<PagingData<Games>>
    fun getGamesFavorite(favoriteGamesId: Int): Flow<FavoriteGames?>
    suspend fun insertFavoriteGame(favoriteGamesId: Int)
    suspend fun deleteFavoriteGame(favoriteGamesId: Int)
}