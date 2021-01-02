package com.arudo.blown.core.ui.ui.home

import androidx.lifecycle.ViewModel
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class HomeViewModel(iBlownUseCase: IBlownUseCase) : ViewModel() {
   val games = iBlownUseCase.getGames()
}