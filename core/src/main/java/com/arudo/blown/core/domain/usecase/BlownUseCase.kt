package com.arudo.blown.core.domain.usecase

import androidx.paging.PagedList
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.domain.repository.IBlownRepository
import com.arudo.blown.core.source.local.Resource
import kotlinx.coroutines.flow.Flow

class BlownUseCase(private val iBlownRepository: IBlownRepository) : IBlownUseCase {
    override fun getGames(): Flow<Resource<List<Games>>> = iBlownRepository.getGames()

    override fun getDetailGame(gamesId: Int): Flow<Resource<Games>> =
        iBlownRepository.getDetailGame(gamesId)

    override fun getSearchGames(search: String): Flow<Resource<List<Games>>> =
        iBlownRepository.getSearchGames(search)

    override fun getListGamesFavorites(): Flow<PagedList<Games>> =
        iBlownRepository.getListGamesFavorites()

    override fun getGamesFavorite(favoriteGamesId: Int): Flow<FavoriteGames?> =
        iBlownRepository.getGamesFavorite(favoriteGamesId)

    override suspend fun insertFavoriteGame(favoriteGamesId: Int) =
        iBlownRepository.insertFavoriteGame(favoriteGamesId)

    override suspend fun deleteFavoriteGame(favoriteGamesId: Int) =
        iBlownRepository.deleteFavoriteGame(favoriteGamesId)
}