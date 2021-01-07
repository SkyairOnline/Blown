package com.arudo.blown.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.arudo.blown.core.data.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.data.source.local.entity.GamesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlownDao {
    @Query("SELECT * FROM gamesEntity")
    fun getGames(): Flow<List<GamesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GamesEntity>)

    @Query("SELECT * FROM gamesEntity where id = :id")
    fun getDetailGames(id: Int): Flow<GamesEntity>

    @Update
    suspend fun updateDetailGames(games: GamesEntity)

    @Query("SELECT * From gamesEntity a join favoriteGamesEntity b on a.id = b.id")
    fun getListGamesFavorites(): DataSource.Factory<Int, GamesEntity>

    @Query("SELECT * FROM favoriteGamesEntity where id = :id")
    fun getGamesFavorite(id: Int): Flow<FavoriteGamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGame(favoriteGames: FavoriteGamesEntity)

    @Delete
    suspend fun deleteFavoriteGame(favoriteGames: FavoriteGamesEntity)
}