package com.arudo.blown.di

import com.arudo.blown.core.domain.usecase.BlownUseCase
import com.arudo.blown.core.domain.usecase.IBlownUseCase
import com.arudo.blown.ui.detail.DetailViewModel
import com.arudo.blown.ui.main.home.HomeViewModel
import com.arudo.blown.ui.main.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IBlownUseCase> {
        BlownUseCase(get())
    }
}

val viewModelModule = module {
    factory {
        Dispatchers.IO
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        DetailViewModel(get(), get())
    }
}