package com.arudo.blown.core.domain.usecase

import androidx.lifecycle.LiveData
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.vo.Resource

interface IBlownUseCase {
    fun getGames(): LiveData<Resource<List<Games>>>
}