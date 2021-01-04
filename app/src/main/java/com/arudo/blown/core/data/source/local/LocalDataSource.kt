package com.arudo.blown.core.data.source.local

import androidx.paging.DataSource
import com.arudo.blown.core.data.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.data.source.local.entity.GamesEntity
import com.arudo.blown.core.data.source.local.room.BlownDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val blownDao: BlownDao) {
    companion object {
        private var localDataSource: LocalDataSource? = null
        fun getInstance(blownDao: BlownDao): LocalDataSource {
            return localDataSource ?: LocalDataSource(blownDao)
        }
    }

    fun getGames(): Flow<List<GamesEntity>> = blownDao.getGames()

    suspend fun insertGames(games: List<GamesEntity>) = blownDao.insertGames(games)

    fun getDetailGames(id: Int): Flow<GamesEntity> = blownDao.getDetailGames(id)

    fun updateDetailGames(games: GamesEntity) = blownDao.updateDetailGames(games)

    fun getListGamesFavorites(): DataSource.Factory<Int, GamesEntity> =
        blownDao.getListGamesFavorites()

    fun getGamesFavorite(id: Int): Flow<FavoriteGamesEntity> = blownDao.getGamesFavorite(id)

    suspend fun insertFavoriteGame(favoriteGames: FavoriteGamesEntity) =
        blownDao.insertFavoriteGame(favoriteGames)

    suspend fun deleteFavoriteGame(favoriteGames: FavoriteGamesEntity) =
        blownDao.deleteFavoriteGame(favoriteGames)
}