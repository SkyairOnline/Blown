package com.arudo.blown.core.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arudo.blown.core.di.Injection
import com.arudo.blown.core.domain.usecase.IBlownUseCase

class ViewModelFactory private constructor(private val blownUseCase: IBlownUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var viewModelFactory: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return viewModelFactory ?: synchronized(this) {
                viewModelFactory ?: ViewModelFactory(Injection.provideUseCase(context))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}