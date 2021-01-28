package com.arudo.blown.di

import com.arudo.blown.core.domain.usecase.BlownUseCase
import com.arudo.blown.core.domain.usecase.IBlownUseCase
import com.arudo.blown.core.ui.detail.DetailViewModel
import com.arudo.blown.core.ui.main.home.HomeViewModel
import com.arudo.blown.core.ui.main.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IBlownUseCase> {
        BlownUseCase(get())
    }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        DetailViewModel(get())
    }
}