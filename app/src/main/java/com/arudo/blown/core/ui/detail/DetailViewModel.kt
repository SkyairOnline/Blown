package com.arudo.blown.core.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class DetailViewModel(iBlownUseCase: IBlownUseCase) : ViewModel() {
    private val gamesId = MutableLiveData<Int>()

    fun setGameDetailId(gamesId: Int) {
        this.gamesId.value = gamesId
    }

    val games = Transformations.switchMap(gamesId) {
        iBlownUseCase.getDetailGame(it).asLiveData()
    }
}