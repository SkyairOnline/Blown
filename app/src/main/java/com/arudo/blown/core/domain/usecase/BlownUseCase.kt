package com.arudo.blown.core.domain.usecase

import androidx.lifecycle.LiveData
import com.arudo.blown.core.data.source.BlownRepository
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.vo.Resource

class BlownUseCase(private val blownRepository: BlownRepository) : IBlownUseCase {
    override fun getGames(): LiveData<Resource<List<Games>>> = blownRepository.getGames()
}