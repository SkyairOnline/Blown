package com.arudo.blown.core.domain.usecase

import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.domain.repository.IBlownRepository
import kotlinx.coroutines.flow.Flow

class BlownUseCase(private val iBlownRepository: IBlownRepository) : IBlownUseCase {
    override fun getGames(): Flow<Resource<List<Games>>> = iBlownRepository.getGames()
    override fun getDetailGame(gamesId: Int): Flow<Resource<Games>> =
        iBlownRepository.getDetailGame(gamesId)
}