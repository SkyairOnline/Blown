package com.arudo.blown.core.domain.usecase

import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.domain.model.Games
import kotlinx.coroutines.flow.Flow

interface IBlownUseCase {
    fun getGames(): Flow<Resource<List<Games>>>
    fun getDetailGame(gamesId: Int): Flow<Resource<Games>>
}