package com.arudo.blown.core.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.domain.repository.IBlownRepository
import com.arudo.blown.core.source.local.LocalDataSource
import com.arudo.blown.core.source.local.Resource
import com.arudo.blown.core.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.source.remote.RemoteDataSource
import com.arudo.blown.core.utils.DataMapper
import com.arudo.blown.core.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class BlownRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IBlownRepository {

    companion object {
        private const val pageSize = 20
    }

    override fun getGames(): Flow<PagingData<Games>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = BlownGamesMediator(localDataSource, remoteDataSource),
        pagingSourceFactory = {
            localDataSource.getGames()
        }
    ).flow.map {
        DataMapper.mapListGamesEntitiesToDomain(it)
    }


    override fun getDetailGame(gamesId: Int): Flow<Resource<Games>> = networkBoundResource(
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


    override fun getSearchGames(search: String): Flow<PagingData<Games>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = BlownGamesMediator(localDataSource, remoteDataSource, search),
        pagingSourceFactory = {
            localDataSource.getGames()
        }
    ).flow.map {
        DataMapper.mapListGamesEntitiesToDomain(it)
    }

    override fun getListGamesFavorites(): Flow<PagingData<Games>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = BlownGamesMediator(localDataSource, remoteDataSource),
        pagingSourceFactory = {
            localDataSource.getListGamesFavorites()
        }
    ).flow.map {
        DataMapper.mapListGamesEntitiesToDomain(it)
    }

    override fun getGamesFavorite(favoriteGamesId: Int): Flow<FavoriteGames?> =
        localDataSource.getGamesFavorite(favoriteGamesId).map {
            DataMapper.mapFavoriteGamesEntitiesToDomain(it)
        }

    override suspend fun insertFavoriteGame(favoriteGamesId: Int) =
        localDataSource.insertFavoriteGame(FavoriteGamesEntity(favoriteGamesId))

    override suspend fun deleteFavoriteGame(favoriteGamesId: Int) =
        localDataSource.deleteFavoriteGame(FavoriteGamesEntity(favoriteGamesId))
}