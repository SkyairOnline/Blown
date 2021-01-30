package com.arudo.blown.core.di

import androidx.room.Room
import com.arudo.blown.core.BuildConfig
import com.arudo.blown.core.domain.repository.IBlownRepository
import com.arudo.blown.core.source.BlownRepository
import com.arudo.blown.core.source.local.LocalDataSource
import com.arudo.blown.core.source.local.room.BlownDatabase
import com.arudo.blown.core.source.remote.RemoteDataSource
import com.arudo.blown.core.source.remote.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<BlownDatabase>().blownDao()
    }
    single {
        Room
            .databaseBuilder(
                androidContext(),
                BlownDatabase::class.java,
                "Blown.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}

val apiModule = module {
    single {
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    factory {
        BuildConfig.KEY
    }
    single {
        LocalDataSource(get())
    }
    single {
        RemoteDataSource(get(), get(), get())
    }
    single<IBlownRepository> {
        BlownRepository(get(), get())
    }
}