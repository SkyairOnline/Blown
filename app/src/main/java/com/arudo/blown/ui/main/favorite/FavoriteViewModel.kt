package com.arudo.blown.ui.main.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arudo.blown.core.domain.usecase.IBlownUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val iBlownUseCase: IBlownUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    val favoriteGames = iBlownUseCase.getListGamesFavorites().asLiveData()

    fun deleteFavoriteGames(favoriteGamesId: Int) = viewModelScope.launch(coroutineDispatcher) {
        iBlownUseCase.deleteFavoriteGame(favoriteGamesId)
    }
}