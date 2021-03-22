package com.arudo.blown.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class HomeViewModel(iBlownUseCase: IBlownUseCase) : ViewModel() {
    val games = iBlownUseCase.getGames().cachedIn(viewModelScope)
}