package com.arudo.blown.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arudo.blown.core.domain.usecase.IBlownUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val iBlownUseCase: IBlownUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    val favoriteGames = iBlownUseCase.getListGamesFavorites().cachedIn(viewModelScope)

    fun deleteFavoriteGames(favoriteGamesId: Int) = viewModelScope.launch(coroutineDispatcher) {
        iBlownUseCase.deleteFavoriteGame(favoriteGamesId)
    }
}