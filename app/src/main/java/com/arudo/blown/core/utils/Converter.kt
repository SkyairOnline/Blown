package com.arudo.blown.core.utils

import androidx.room.TypeConverter
import com.arudo.blown.core.data.source.local.entity.GamePlatformMetacriticEntity
import com.arudo.blown.core.data.source.local.entity.PlatformEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromPlatformList(platformEntity: List<PlatformEntity>): String {
        val type = object : TypeToken<List<PlatformEntity>>() {}.type
        return Gson().toJson(platformEntity, type)
    }

    @TypeConverter
    fun toPlatformList(platformEntity: String): List<PlatformEntity> {
        val type = object : TypeToken<List<PlatformEntity>>() {}.type
        return Gson().fromJson<List<PlatformEntity>>(platformEntity, type)
    }

    @TypeConverter
    fun fromGamePlatformMetacriticList(gamePlatformMetacriticEntity: List<GamePlatformMetacriticEntity>): String {
        val type = object : TypeToken<List<GamePlatformMetacriticEntity>>() {}.type
        return Gson().toJson(gamePlatformMetacriticEntity, type)
    }

    @TypeConverter
    fun toGamePlatformMetacriticList(gamePlatformMetacriticEntity: String): List<GamePlatformMetacriticEntity> {
        val type = object : TypeToken<List<GamePlatformMetacriticEntity>>() {}.type
        return Gson().fromJson<List<GamePlatformMetacriticEntity>>(
            gamePlatformMetacriticEntity,
            type
        )
    }
}