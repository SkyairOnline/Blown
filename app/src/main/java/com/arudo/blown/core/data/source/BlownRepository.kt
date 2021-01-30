package com.arudo.blown.core.data.source

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arudo.blown.core.data.source.local.LocalDataSource
import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.data.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.data.source.remote.RemoteDataSource
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.domain.repository.IBlownRepository
import com.arudo.blown.core.utils.DataMapper
import com.arudo.blown.core.utils.NetworkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BlownRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IBlownRepository {

    override fun getGames(): Flow<Resource<List<Games>>> = NetworkBoundResource(
        {
            localDataSource.getGames().map {
                DataMapper.mapListGamesEntitiesToDomain(it)
            }
        },
        { remoteDataSource.getGames() },
        {
            val gamesList = DataMapper.mapListGamesResponseToEntities(it.results)
            localDataSource.insertGames(gamesList)
        }
    )

    override fun getDetailGame(gamesId: Int): Flow<Resource<Games>> = NetworkBoundResource(
        {
            localDataSource.getDetailGames(gamesId).map {
                DataMapper.mapGamesEntitiesToDomain(it)
            }
        },
        { remoteDataSource.getDetailGames(gamesId) },
        {
            val gamesList = DataMapper.mapGamesResponseToEntities(it)
            localDataSource.updateDetailGames(gamesList)
        }
    )


    override fun getSearchGames(search: String): Flow<Resource<List<Games>>> = NetworkBoundResource(
        {
            localDataSource.getSearchGames(search).map {
                DataMapper.mapListGamesEntitiesToDomain(it)
            }
        },
        { remoteDataSource.getSearchGames(search) },
        {
            val gamesList = DataMapper.mapListGamesResponseToEntities(it.results)
            localDataSource.insertGames(gamesList)
        }
    )

    override fun getListGamesFavorites(): Flow<PagedList<Games>> = LivePagedListBuilder(
        localDataSource.getListGamesFavorites().map { DataMapper.mapGamesEntitiesToDomain(it) },
        PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(20)
            .setPageSize(20).build()
    ).build().asFlow()


    override fun getGamesFavorite(favoriteGamesId: Int): Flow<FavoriteGames?> =
        localDataSource.getGamesFavorite(favoriteGamesId).map {
            DataMapper.mapFavoriteGamesEntitiesToDomain(it)
        }

    override suspend fun insertFavoriteGame(favoriteGamesId: Int) =
        localDataSource.insertFavoriteGame(FavoriteGamesEntity(favoriteGamesId))

    override suspend fun deleteFavoriteGame(favoriteGamesId: Int) =
        localDataSource.deleteFavoriteGame(FavoriteGamesEntity(favoriteGamesId))
}