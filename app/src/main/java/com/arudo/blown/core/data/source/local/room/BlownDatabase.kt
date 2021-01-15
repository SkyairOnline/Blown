package com.arudo.blown.core.data.source.local.room

import androidx.room.Database
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
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class BlownDatabase : RoomDatabase() {
    abstract fun blownDao(): BlownDao
}