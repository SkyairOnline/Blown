package com.arudo.blown.core.source.local

import androidx.paging.PagingSource
import com.arudo.blown.core.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.source.local.entity.GamesEntity
import com.arudo.blown.core.source.local.entity.RemotePageKeysEntity
import com.arudo.blown.core.source.local.room.BlownDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val blownDao: BlownDao) {

    fun getGames(): PagingSource<Int, GamesEntity> = blownDao.getGames()

    fun getSearchGames(search: String): PagingSource<Int, GamesEntity> =
        blownDao.getSearchGames("$search%")

    suspend fun insertGames(games: List<GamesEntity>) = blownDao.insertGames(games)

    suspend fun deleteAllGames() = blownDao.deleteAllGames()

    fun getDetailGames(id: Int): Flow<GamesEntity> = blownDao.getDetailGames(id)

    suspend fun updateDetailGames(games: GamesEntity) = blownDao.updateDetailGames(games)

    fun getListGamesFavorites(): Flow<List<FavoriteGamesEntity>> = blownDao.getListGamesFavorites()

    fun getGamesFavorite(id: Int): Flow<FavoriteGamesEntity> = blownDao.getGamesFavorite(id)

    suspend fun insertFavoriteGame(favoriteGames: FavoriteGamesEntity) =
        blownDao.insertFavoriteGame(favoriteGames)

    suspend fun deleteFavoriteGame(favoriteGames: FavoriteGamesEntity) =
        blownDao.deleteFavoriteGame(favoriteGames)

    suspend fun insertRemoteKeysGameAll(remoteKey: List<RemotePageKeysEntity>) =
        blownDao.insertAll(remoteKey)

    suspend fun remoteKeysGamesId(gamesId: Int): RemotePageKeysEntity? =
        blownDao.remoteKeysGamesId(gamesId)

    suspend fun clearRemoteKeys() = blownDao.clearRemoteKeys()
}