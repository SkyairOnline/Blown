package com.arudo.blown.core.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.arudo.blown.core.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.source.local.entity.GamesEntity
import com.arudo.blown.core.source.local.entity.RemotePageKeysEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlownDao {
    @Query("SELECT * FROM gamesEntity order by added DESC")
    fun getGames(): PagingSource<Int, GamesEntity>

    @Query("SELECT * FROM gamesEntity where name like :search")
    fun getSearchGames(search: String): PagingSource<Int, GamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GamesEntity>)

    @Query("SELECT * FROM gamesEntity where gamesId = :id")
    fun getDetailGames(id: Int): Flow<GamesEntity>

    @Update
    suspend fun updateDetailGames(games: GamesEntity)

    @Query("SELECT * From favoriteGamesEntity")
    fun getListGamesFavorites(): Flow<List<FavoriteGamesEntity>>

    @Query("SELECT * FROM favoriteGamesEntity where gamesId = :id")
    fun getGamesFavorite(id: Int): Flow<FavoriteGamesEntity>

    @Query("DELETE FROM gamesEntity")
    suspend fun deleteAllGames()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGame(favoriteGames: FavoriteGamesEntity)

    @Delete
    suspend fun deleteFavoriteGame(favoriteGames: FavoriteGamesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemotePageKeysEntity>)

    @Query("SELECT * FROM remoteKeys where id =:gamesId")
    suspend fun remoteKeysGamesId(gamesId: Int): RemotePageKeysEntity?

    @Query("DELETE FROM remoteKeys")
    suspend fun clearRemoteKeys()
}