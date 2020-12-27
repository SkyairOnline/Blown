package com.arudo.blown.core.di

import android.content.Context
import com.arudo.blown.core.data.source.BlownRepository
import com.arudo.blown.core.data.source.local.LocalDataSource
import com.arudo.blown.core.data.source.local.room.BlownDatabase
import com.arudo.blown.core.data.source.remote.RemoteDataSource
import com.arudo.blown.core.data.source.remote.network.ApiConfig
import kotlinx.coroutines.Dispatchers

object Injection {
    fun provideRepository(context: Context): BlownRepository {
        val blownDatabase = BlownDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(blownDatabase.blownDao())
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val dispatcher = Dispatchers.IO
        return BlownRepository.getInstance(localDataSource, remoteDataSource, dispatcher)
    }
}