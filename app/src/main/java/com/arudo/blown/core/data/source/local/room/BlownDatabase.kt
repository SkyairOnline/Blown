package com.arudo.blown.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arudo.blown.core.data.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.data.source.local.entity.GamesEntity
import com.arudo.blown.core.utils.Converter

@Database(
    entities = [
        GamesEntity::class,
        FavoriteGamesEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converter::class)
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