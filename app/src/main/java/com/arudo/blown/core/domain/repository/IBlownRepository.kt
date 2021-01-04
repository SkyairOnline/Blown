package com.arudo.blown.core.domain.repository

import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IBlownRepository {
    fun getGames(): Flow<Resource<List<Games>>>
}