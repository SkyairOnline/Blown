package com.arudo.blown.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class FavoriteViewModel(
    iBlownUseCase: IBlownUseCase
) : ViewModel() {
    val favoriteGames = iBlownUseCase.getListGamesFavorites().asLiveData()
}