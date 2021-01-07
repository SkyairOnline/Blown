package com.arudo.blown.core.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class HomeViewModel(iBlownUseCase: IBlownUseCase) : ViewModel() {
   val games = iBlownUseCase.getGames().asLiveData()
}