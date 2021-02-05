package com.arudo.blown.ui.detail

import androidx.lifecycle.*
import com.arudo.blown.core.domain.usecase.IBlownUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val iBlownUseCase: IBlownUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val gamesId = MutableLiveData<Int>()

    fun setGameDetailId(gamesId: Int) {
        this.gamesId.value = gamesId
    }

    val games = Transformations.switchMap(gamesId) {
        iBlownUseCase.getDetailGame(it).asLiveData()
    }

    fun favoriteGames(favoriteGamesId: Int) =
        iBlownUseCase.getGamesFavorite(favoriteGamesId).asLiveData()

    fun insertFavoriteGames(favoriteGamesId: Int) = viewModelScope.launch(coroutineDispatcher) {
        iBlownUseCase.insertFavoriteGame(favoriteGamesId)
    }

    fun deleteFavoriteGames(favoriteGamesId: Int) = viewModelScope.launch(coroutineDispatcher) {
        iBlownUseCase.deleteFavoriteGame(favoriteGamesId)
    }

}