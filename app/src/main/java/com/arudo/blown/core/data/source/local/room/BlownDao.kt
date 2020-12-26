package com.arudo.blown.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arudo.blown.core.data.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.data.source.local.entity.GamesEntity

@Dao
interface BlownDao {
    @Query("SELECT * FROM gamesEntity")
    fun getGames(): LiveData<List<GamesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<GamesEntity>)

    @Query("SELECT * FROM gamesEntity where id = :id")
    fun getDetailGames(id: Int): LiveData<GamesEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDetailGames(games: GamesEntity)

    @Query("SELECT * From gamesEntity a join favoriteGamesEntity b on a.id = b.id order by createdDate desc")
    fun getListFavorites(games: List<GamesEntity>)

    @Query("SELECT * FROM favoriteGamesEntity where id = :id")
    fun getGamesFavorite(id: Int): LiveData<FavoriteGamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGame(favoriteGames: FavoriteGamesEntity)

    @Delete
    suspend fun deleteFavoriteGame(favoriteGames: FavoriteGamesEntity)
}