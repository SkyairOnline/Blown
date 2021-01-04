package com.arudo.blown.core.di

import android.content.Context
import com.arudo.blown.core.data.source.BlownRepository
import com.arudo.blown.core.data.source.local.LocalDataSource
import com.arudo.blown.core.data.source.local.room.BlownDatabase
import com.arudo.blown.core.data.source.remote.RemoteDataSource
import com.arudo.blown.core.data.source.remote.network.ApiConfig
import com.arudo.blown.core.domain.usecase.BlownUseCase
import com.arudo.blown.core.domain.usecase.IBlownUseCase
import kotlinx.coroutines.Dispatchers

object Injection {
    private fun provideRepository(context: Context): BlownRepository {
        val blownDatabase = BlownDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(blownDatabase.blownDao())
        val dispatcher = Dispatchers.IO
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService(), dispatcher)
        return BlownRepository.getInstance(localDataSource, remoteDataSource)
    }

    fun provideUseCase(context: Context): IBlownUseCase {
        val repository = provideRepository(context)
        return BlownUseCase(repository)
    }
}