package com.arudo.blown.ui.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class SearchViewModel(private val iBlownUseCase: IBlownUseCase) : ViewModel() {

    fun searchGames(searchText: String) =
        iBlownUseCase.getSearchGames(searchText).cachedIn(viewModelScope)

}