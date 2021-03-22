package com.arudo.blown.core.domain.usecase

import androidx.paging.PagingData
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.domain.repository.IBlownRepository
import com.arudo.blown.core.source.local.Resource
import kotlinx.coroutines.flow.Flow

class BlownUseCase(private val iBlownRepository: IBlownRepository) : IBlownUseCase {
    override fun getGames(): Flow<PagingData<Games>> = iBlownRepository.getGames()

    override fun getDetailGame(gamesId: Int): Flow<Resource<Games>> =
        iBlownRepository.getDetailGame(gamesId)

    override fun getSearchGames(search: String): Flow<PagingData<Games>> =
        iBlownRepository.getSearchGames(search)

    override fun getListGamesFavorites(): Flow<List<FavoriteGames>> =
        iBlownRepository.getListGamesFavorites()

    override fun getGamesFavorite(favoriteGamesId: Int): Flow<FavoriteGames?> =
        iBlownRepository.getGamesFavorite(favoriteGamesId)

    override suspend fun insertFavoriteGame(favoriteGames: FavoriteGames) =
        iBlownRepository.insertFavoriteGame(favoriteGames)

    override suspend fun deleteFavoriteGame(favoriteGames: FavoriteGames) =
        iBlownRepository.deleteFavoriteGame(favoriteGames)
}