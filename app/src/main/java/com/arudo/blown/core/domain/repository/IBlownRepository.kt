package com.arudo.blown.core.domain.repository

import androidx.lifecycle.LiveData
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.vo.Resource

interface IBlownRepository {
    fun getGames(): LiveData<Resource<List<Games>>>
}