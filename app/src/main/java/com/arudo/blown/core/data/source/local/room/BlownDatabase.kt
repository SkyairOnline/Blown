package com.arudo.blown.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arudo.blown.core.data.source.local.entity.*

@Database(
        entities = [
            DetailPlatformEntity::class,
            EsrbRatingEntity::class,
            GamePlatformMetacriticEntity::class,
            GamesEntity::class,
            FavoriteGamesEntity::class
        ],
        version = 1,
        exportSchema = false
)
abstract class BlownDatabase : RoomDatabase() {
    abstract fun blownDao(): BlownDao

    companion object {
        @Volatile
        private var blownDatabase: BlownDatabase? = null

        fun getInstance(context: Context): BlownDatabase =
                blownDatabase ?: synchronized(this) {
                    blownDatabase ?: Room
                            .databaseBuilder(
                                    context.applicationContext,
                                    BlownDatabase::class.java,
                                    "Blown.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build()
                }
    }
}