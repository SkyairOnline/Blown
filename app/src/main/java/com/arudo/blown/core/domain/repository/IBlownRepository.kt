package com.arudo.blown.core.domain.repository

import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.domain.model.Games
import kotlinx.coroutines.flow.Flow

interface IBlownRepository {
    fun getGames(): Flow<Resource<List<Games>>>
    fun getDetailGame(gamesId: Int): Flow<Resource<Games>>
}