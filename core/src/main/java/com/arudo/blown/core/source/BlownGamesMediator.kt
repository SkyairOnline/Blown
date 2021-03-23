package com.arudo.blown.core.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.arudo.blown.core.source.local.LocalDataSource
import com.arudo.blown.core.source.local.entity.GamesEntity
import com.arudo.blown.core.source.local.entity.RemotePageKeysEntity
import com.arudo.blown.core.source.remote.RemoteDataSource
import com.arudo.blown.core.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class BlownGamesMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val searchQuery: String? = null
) : RemoteMediator<Int, GamesEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GamesEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If the previous key is null, then the list is empty so we should wait for data
                // fetched by remote refresh and can simply skip loading this time by returning
                // `false` for endOfPaginationReached.
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If the next key is null, then the list is empty so we should wait for data
                // fetched by remote refresh and can simply skip loading this time by returning
                // `false` for endOfPaginationReached.
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                nextKey
            }
        }
        try {
            val remoteResponse = if (searchQuery == null) {
                remoteDataSource.getGames(page, state.config.pageSize)
            } else {
                remoteDataSource.getSearchGames(searchQuery, page, state.config.pageSize)
            }
            val remoteResponseItem = remoteResponse.results
            val endPageReach = remoteResponseItem.isEmpty()
            if (loadType == LoadType.REFRESH) {
                localDataSource.clearRemoteKeys()
                localDataSource.deleteAllGames()
            }
            val keys = remoteResponseItem.map {
                RemotePageKeysEntity(
                    id = it.gamesId,
                    prevKey = remoteResponse.prev?.substringAfter("page=")?.substringBefore("&")
                        ?.toInt(),
                    nextKey = remoteResponse.next?.substringAfter("page=")?.substringBefore("&")
                        ?.toInt()
                )
            }
            localDataSource.insertRemoteKeysGameAll(keys)
            localDataSource.insertGames(
                DataMapper.mapListGamesResponseToEntities(remoteResponseItem)
            )
            return MediatorResult.Success(endOfPaginationReached = endPageReach)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GamesEntity>): RemotePageKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                // Get the remote keys of the first items retrieved
                localDataSource.remoteKeysGamesId(it.gamesId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GamesEntity>): RemotePageKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                // Get the remote keys of the last item retrieved
                localDataSource.remoteKeysGamesId(it.gamesId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GamesEntity>
    ): RemotePageKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.gamesId?.let { gamesId ->
                localDataSource.remoteKeysGamesId(gamesId)
            }
        }
    }

}