package com.arudo.blown.core.domain.usecase

import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IBlownUseCase {
    fun getGames(): Flow<Resource<List<Games>>>
}