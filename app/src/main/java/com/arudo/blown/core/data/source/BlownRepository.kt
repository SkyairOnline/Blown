package com.arudo.blown.core.data.source

import com.arudo.blown.core.data.source.local.LocalDataSource
import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.data.source.remote.RemoteDataSource
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

    override fun getGames(): Flow<Resource<List<Games>>> {
        return NetworkBoundResource(
            {
                localDataSource.getGames().map {
                    DataMapper.mapGamesEntitiesToDomain(it)
                }
            },
            { remoteDataSource.getGames() },
            {
                val gamesList = DataMapper.mapGamesResponseToEntities(it.results)
                localDataSource.insertGames(gamesList)
            }
        )
    }

    override fun getDetailGame(gamesId: Int): Flow<Resource<Games>> {
        return NetworkBoundResource(
            {
                localDataSource.getDetailGames(gamesId).map {
                    DataMapper.mapDetailGamesEntitiesToDomain(it)
                }
            },
            { remoteDataSource.getDetailGames(gamesId) },
            {
                val gamesList = DataMapper.mapDetailGamesResponseToEntities(it)
                localDataSource.updateDetailGames(gamesList)
            }
        )
    }
}