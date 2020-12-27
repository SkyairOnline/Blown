package com.arudo.blown.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.arudo.blown.core.data.source.local.LocalDataSource
import com.arudo.blown.core.data.source.remote.RemoteDataSource
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.DataMapper
import com.arudo.blown.core.utils.NetworkBoundResource
import com.arudo.blown.core.utils.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher

class BlownRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    companion object {
        @Volatile
        private var blownRepository: BlownRepository? = null

        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource,
            coroutineDispatcher: CoroutineDispatcher
        ): BlownRepository = blownRepository ?: synchronized(this) {
            blownRepository ?: BlownRepository(
                localDataSource,
                remoteDataSource,
                coroutineDispatcher
            )
        }
    }

    fun getGames(): LiveData<Resource<List<Games>>> {
        return NetworkBoundResource(
            {
                Transformations.map(localDataSource.getGames()) {
                    DataMapper.mapGamesEntitiesToDomain(it)
                }
            },
            { remoteDataSource.getGames() },
            {
                val gamesList = DataMapper.mapGamesResponseToEntities(it.results)
                localDataSource.insertGames(gamesList)
            },
            coroutineDispatcher
        )
    }
}