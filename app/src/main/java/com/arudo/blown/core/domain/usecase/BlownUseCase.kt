package com.arudo.blown.core.domain.usecase

import com.arudo.blown.core.data.source.BlownRepository
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.vo.Resource
import kotlinx.coroutines.flow.Flow

class BlownUseCase(private val blownRepository: BlownRepository) : IBlownUseCase {
    override fun getGames(): Flow<Resource<List<Games>>> = blownRepository.getGames()
}