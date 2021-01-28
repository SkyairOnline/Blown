package com.arudo.blown.core.ui.main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class SearchViewModel(iBlownUseCase: IBlownUseCase) : ViewModel() {
    private val searchText = MutableLiveData<String>()

    fun setGameDetailId(searchText: String) {
        this.searchText.value = searchText
    }

    val searchGames = Transformations.switchMap(searchText) {
        iBlownUseCase.getSearchGames(it).asLiveData()
    }
}