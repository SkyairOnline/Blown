package com.arudo.blown.core.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arudo.blown.core.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.source.local.entity.GamesEntity
import com.arudo.blown.core.source.local.entity.RemotePageKeysEntity
import com.arudo.blown.core.utils.Converter

@Database(
    entities = [
        GamesEntity::class,
        FavoriteGamesEntity::class,
        RemotePageKeysEntity::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class BlownDatabase : RoomDatabase() {
    abstract fun blownDao(): BlownDao
}